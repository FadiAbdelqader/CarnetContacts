import React, {useState} from "react";
import {HomePage} from "./Mycontacts";
import {LogOut} from "./Homepage";
import {useNavigate} from "react-router-dom";

export default function Home() {
    return (
        <>
            <div className="d-flex justify-content-start">
                <div className="me-2">
                    <HomePage/>
                </div>
                <div>
                    <LogOut/>
                </div>
            </div>
            <CreateContactForm/>
        </>
    );
}

function CreateContactForm() {
    const inputStyle = {border: '3px solid', padding: '0.375rem 0.75rem'};
    const navigate = useNavigate();
    const [state, setState] = useState({});

    const handleChangeStreetNumber = (event) => {
        const number = event.target.value;
        setState({...state, number: number});
    }
    const handleChangeStreet = (event) => {
        const street = event.target.value;
        setState({...state, street: street});
    }
    const handleChangeCity = (event) => {
        const city = event.target.value;
        setState({...state, city: city});
    }
    const handleChangeZip = (event) => {
        const zip = event.target.value;
        setState({...state, zip: zip});
    }
    const handleChangeCountry = (event) => {
        const country = event.target.value;
        setState({...state, country: country});
    }
    const handleChangeFirstName = (event) => {
        const firstName = event.target.value;
        setState({...state, firstName: firstName});
    }
    const handleChangeLastName = (event) => {
        const lastName = event.target.value;
        setState({...state, lastName: lastName});
    }
    const handleChangeEmail = (event) => {
        const email = event.target.value;
        setState({...state, email: email});
    }
    const handleChangePhoneNumber = (event) => {
        const phoneNumber = event.target.value;
        setState({...state, phoneNumber: phoneNumber});
    }
    const handleChangePhoneKind = (event) => {
        const phoneKind = event.target.value;
        setState({...state, phoneKind: phoneKind});
    }

    async function handleSubmitAddress(event) {
        event.preventDefault();

        const userData = {
            number: state.number,
            street: state.street,
            city: state.city,
            zip: state.zip,
            country: state.country
        };

        const requestOptions = {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(userData)
        };

        try {
            const response = await fetch('http://localhost:8080/createaddress', requestOptions);
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            const idAddress = await response.json();
            console.log("Address created with ID:", idAddress);
            return idAddress;
        } catch (error) {
            console.log("Error in submitting address:", error.message);
            return null;
        }
    }

    async function handleSubmitContact(event) {

        event.preventDefault();

        const idAddress = await handleSubmitAddress(event);
        console.log("l\'id de l\'adresse retourné est : ", idAddress);
        const userData = {
            firstName: state.firstName,
            lastName: state.lastName,
            email: state.email,
            address: {
                idAddress: idAddress
            }
        };


        const requestOptions = {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(userData)
        };

        try {
            const response = await fetch('http://localhost:8080/createcontact', requestOptions);
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            const idContact = await response.json();
            console.log("Contact created with ID:", idContact);
            return idContact;
        } catch (error) {
            console.log("Error in submitting address:", error.message);
            return null;
        }
    }

    const handleSubmitNumberPhone = async (event) => {
        event.preventDefault();

        const idContact = await handleSubmitContact(event);
        console.log("l\'id du contact retourné est : ", idContact);

        const phoneData = {
            phoneKind: state.phoneKind,
            phoneNumber: state.phoneNumber,
            contact: {
                idContact: idContact
            }
        }

        const requestOptions = {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(phoneData)
        };
        fetch('http://localhost:8080/createphone', requestOptions)
            .then(response => navigate("/myContact"))
            .catch(error => console.log(error.message))
    }

    return (
        <div className="container py-5">
            <h2 className="text-center mb-4">Add a New Contact</h2>
            <form onSubmit={handleSubmitNumberPhone} className="row g-3">
                <InputField id="firstName" label="First Name" value={state.firstName} onChange={handleChangeFirstName} />
                <InputField id="lastName" label="Last Name" value={state.lastName} onChange={handleChangeLastName} />
                <InputField id="email" label="E-mail" value={state.email} onChange={handleChangeEmail} />
                <InputField id="number" label="Number" value={state.number} onChange={handleChangeStreetNumber} />
                <InputField id="street" label="Street" value={state.street} onChange={handleChangeStreet} />
                <InputField id="city" label="City" value={state.city} onChange={handleChangeCity} />
                <InputField id="zip" label="ZIP Code" value={state.zip} onChange={handleChangeZip} />
                <InputField id="country" label="Country" value={state.country} onChange={handleChangeCountry} />
                <InputField id="phoneKind" label="Phone Kind" value={state.phoneKind} onChange={handleChangePhoneKind} />
                <InputField id="phoneNumber" label="Phone Number" value={state.phoneNumber} onChange={handleChangePhoneNumber} />

                <div className="col-12 text-center">
                    <button type="submit" className="btn btn-primary btn-lg">Submit</button>
                </div>
            </form>
        </div>
    );
}

function InputField({ id, label, value, onChange }) {
    return (
        <div className="col-md-6">
            <label htmlFor={id} className="form-label">{label}</label>
            <input type="text" className="form-control" id={id} name={id} value={value}
                   placeholder={`Enter ${label.toLowerCase()}`} onChange={onChange} />
        </div>
    );
}