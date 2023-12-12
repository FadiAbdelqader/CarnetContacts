import React, {useState} from "react";

export default function Home() {
    return (
        <>
            <CreateContactForm/>
        </>
    );
}

function CreateContactForm() {
    const inputStyle = {border: '3px solid', padding: '0.375rem 0.75rem'};

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
            const idAddress = await response.json(); // Supposant que la réponse est en JSON
            console.log("Address created with ID:", idAddress);
            return idAddress; // Retourne l'ID
        } catch (error) {
            console.log("Error in submitting address:", error.message);
            return null; // Retourne null en cas d'erreur
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
            return null; // Retourne null en cas d'erreur
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
            .then(response => console.log("ok"))
            .catch(error => console.log(error.message))
    }

    return (
        <>
            <div className="container">
                <h1 className="text-center my-4">Add a contact to your contact book</h1>
                <form onSubmit={handleSubmitNumberPhone}>
                    <div className="row">
                        <div className="col-md-4 mb-3">
                            <label htmlFor="number">Number</label>
                            <input type="text" className="form-control" id="number" name="number" value={state.number}
                                   placeholder="Enter number" onChange={handleChangeStreetNumber} style={inputStyle}/>
                        </div>
                        <div className="col-md-4 mb-3">
                            <label htmlFor="street">Street</label>
                            <input type="text" className="form-control" id="street" name="street" value={state.street}
                                   placeholder="Enter street" onChange={handleChangeStreet} style={inputStyle}/>
                        </div>
                        <div className="col-md-4 mb-3">
                            <label htmlFor="city">City</label>
                            <input type="text" className="form-control" id="city" name="city" value={state.city}
                                   placeholder="Enter city" onChange={handleChangeCity} style={inputStyle}/>
                        </div>
                        <div className="col-md-4 mb-3">
                            <label htmlFor="zip">ZIP Code</label>
                            <input type="text" className="form-control" id="zip" name="zip" value={state.zip}
                                   placeholder="Enter ZIP code" onChange={handleChangeZip} style={inputStyle}/>
                        </div>
                        <div className="col-md-4 mb-3">
                            <label htmlFor="country">Country</label>
                            <input type="text" className="form-control" id="country" name="country"
                                   value={state.country} placeholder="Enter country" onChange={handleChangeCountry}
                                   style={inputStyle}/>
                        </div>
                    </div>

                    <div className="row">
                        <div className="col-md-4 mb-3">
                            <label htmlFor="firstName">firstName</label>
                            <input type="text" className="form-control" id="firstName" name="firstName"
                                   value={state.firstName} placeholder="Enter your firstname"
                                   onChange={handleChangeFirstName} style={inputStyle}/>
                        </div>
                        <div className="col-md-4 mb-3">
                            <label htmlFor="lastName">lastname</label>
                            <input type="text" className="form-control" id="lastName" name="lastName"
                                   value={state.lastName} placeholder="Enter your lastname"
                                   onChange={handleChangeLastName} style={inputStyle}/>
                        </div>
                        <div className="col-md-4 mb-3">
                            <label htmlFor="email">E-mail</label>
                            <input type="text" className="form-control" id="email" name="email" value={state.email}
                                   placeholder="Enter your E-mail" onChange={handleChangeEmail} style={inputStyle}/>
                        </div>
                    </div>

                    <div className="row">
                        <div className="col-md-4 mb-3">
                            <label htmlFor="phoneKind">phone Kind</label>
                            <input type="text" className="form-control" id="phoneKind" name="phoneKind"
                                   value={state.phoneKind} placeholder="Enter the phone kind"
                                   onChange={handleChangePhoneKind} style={inputStyle}/>
                        </div>
                        <div className="col-md-4 mb-3">
                            <label htmlFor="phoneNumber">phone Number</label>
                            <input type="text" className="form-control" id="phoneNumber" name="phoneNumber"
                                   value={state.phoneNumber} placeholder="Enter the phoneNumber"
                                   onChange={handleChangePhoneNumber} style={inputStyle}/>
                        </div>
                    </div>

                    <div className="text-center">
                        <button type="submit" className="btn btn-primary btn-lg mt-2">Add Contact</button>
                    </div>
                </form>
            </div>
        </>
    );
}





























