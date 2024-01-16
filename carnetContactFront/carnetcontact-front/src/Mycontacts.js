import React, {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import {LogOut} from "./Homepage";

export default function MyContacts() {
    const [selectedContactId, setSelectedContactId] = useState(null);
    const [contactToUpdate, setContactToUpdate] = useState(null);

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
            <ContactsSummary onContactSelect={setSelectedContactId} onUpdateContactSelect={setContactToUpdate}/>
            <ContactWindow selectedContactId={selectedContactId}/>
            {contactToUpdate && <UpdateContactWindow contactInfo={contactToUpdate}/>}
        </>
    );
}

export function HomePage() {
    const navigate = useNavigate();

    function handleClickButton() {
        navigate('/homepage');
    }

    return (
        <div className="m-3">
            <button className="btn btn-primary" onClick={handleClickButton}>Home</button>
        </div>
    );
}

function ContactsSummary({onContactSelect, onUpdateContactSelect}) {
    const [contacts, setContacts] = useState([]);

    useEffect(() => {
        getMyContacts();
    }, []);

    async function getMyContacts() {
        const option = {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        };

        try {
            const response = await fetch('http://localhost:8080/getAllContact', option);
            if (response.ok) {
                const data = await response.json();
                setContacts(data);
            } else {
                console.error('Récupération des contacts : ERREUR!');
            }
        } catch (error) {
            console.error(error.message);
        }
    }


    async function deleteContacts(idContact) {
        const option = {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
        };

        try {
            const response = await fetch(`http://localhost:8080/deleteContact?idContact=${idContact}`, option);
            if (response.ok) {
                // Supprimez le contact de l'état local après la suppression réussie
                setContacts(contacts.filter(contact => contact.idContact !== idContact));
            } else {
                console.error('Suppression du contact : ERREUR!');
            }
        } catch (error) {
            console.error(error.message);
        }
    }

    return (
        <div className="container mt-4">
            <h2 className="mb-3">Your Contacts</h2>
            <table className="table table-hover">
                <thead className="table-dark">
                <tr>
                    <th>Contacts</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                {contacts.map((contact) => (
                    <tr key={contact.idContact}>
                        <td onClick={() => onContactSelect(contact.idContact)}>
                            {contact.firstName} {contact.lastName}
                        </td>
                        <td>
                            <button className="btn btn-danger me-2" onClick={() => deleteContacts(contact.idContact)}>
                                Delete
                            </button>
                            <button className="btn btn-primary" onClick={() => onUpdateContactSelect(contact)}>
                                Update
                            </button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}

function ContactWindow({selectedContactId}) {
    const [contactInfo, setContactInfo] = useState(null);

    useEffect(() => {
        if (selectedContactId) {
            getContactInfo(selectedContactId);
        }
    }, [selectedContactId]);

    async function getContactInfo(idContact) {
        const option = {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        };

        try {
            const response = await fetch(`http://localhost:8080/getContactInfo?idContact=${idContact}`, option);
            if (response.ok) {
                const data = await response.json();
                setContactInfo(data[0]);
            } else {
                console.error('Récupération des infos du contact : ERREUR!');
            }
        } catch (error) {
            console.error(error.message);
        }
    }

    if (!contactInfo) {
        return <div>Select a contact to see his information</div>;
    }

    return (
        <div className="card mx-3 my-4">
            <div className="card-body">
                <h5 className="card-title">Contact details : </h5>
                <p className="card-text"><strong>First Name:</strong> {contactInfo.firstName}</p>
                <p className="card-text"><strong>Last Name:</strong> {contactInfo.lastName}</p>
                <p className="card-text"><strong>Email:</strong> {contactInfo.email}</p>
                <p className="card-text">
                    <strong>Address:</strong> {contactInfo.number}, {contactInfo.street} in {contactInfo.city} {contactInfo.zip} {contactInfo.country}
                </p>
                <p className="card-text"><strong>Phone Kind:</strong> {contactInfo.phoneKind}</p>
                <p className="card-text"><strong>Phone Number:</strong> {contactInfo.phoneNumber}</p>
            </div>
        </div>
    );
}

function UpdateContactWindow({contactInfo}) {
    const navigate = useNavigate();
    // Décomposition de contactInfo pour faciliter l'accès aux champs
    const {firstName, lastName, email, number, street, city, zip, country, phoneKind, phoneNumber} = contactInfo;

    const [state, setState] = useState({
        firstName: '',
        lastName: '',
        email: '',
        number: '',
        street: '',
        city: '',
        zip: '',
        country: '',
        phoneKind: '',
        phoneNumber: ''
    });

    // Mise à jour de l'état lorsque contactInfo change
    useEffect(() => {
        if (contactInfo) {
            setState({
                firstName: contactInfo.firstName,
                lastName: contactInfo.lastName,
                email: contactInfo.email,
                number: contactInfo.number,
                street: contactInfo.street,
                city: contactInfo.city,
                zip: contactInfo.zip,
                country: contactInfo.country,
                phoneKind: contactInfo.phoneKind,
                phoneNumber: contactInfo.phoneNumber
            });
        }
    }, [contactInfo]);

    const handleChange = (event) => {
        const {name, value} = event.target;
        setState({...state, [name]: value});
    };

    async function handleSubmitContact(event) {
        event.preventDefault();

        const userData = {
            ...state,
            idContact: contactInfo.idContact
        };

        const requestOptions = {
            method: 'PUT',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(userData)
        };

        try {
            const response = await fetch('http://localhost:8080/updateAContact', requestOptions);
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            const idContact = await response.json();
            console.log("Contact updated with ID:", idContact);

        } catch (error) {
            console.error("Error in updating contact:", error.message);
            window.location.reload();
        }
    }

    return (
        <div className="card mx-3 my-4">
            <div className="card-body">
                <h5 className="card-title">Update Contact</h5>
                <form onSubmit={handleSubmitContact} className="row g-3">
                    {createInputField("firstName", "First Name", state.firstName, handleChange)}
                    {createInputField("lastName", "Last Name", state.lastName, handleChange)}
                    {createInputField("email", "Email", state.email, handleChange)}
                    {createInputField("number", "Number", state.number, handleChange)}
                    {createInputField("street", "Street", state.street, handleChange)}
                    {createInputField("city", "City", state.city, handleChange)}
                    {createInputField("zip", "ZIP", state.zip, handleChange)}
                    {createInputField("country", "Country", state.country, handleChange)}
                    {createInputField("phoneKind", "Phone Kind", state.phoneKind, handleChange)}
                    {createInputField("phoneNumber", "Phone Number", state.phoneNumber, handleChange)}
                    <div className="col-12">
                        <button type="submit" className="btn btn-success">Save Changes</button>
                    </div>
                </form>
            </div>
        </div>
    );
}

function createInputField(id, label, value, onChange) {
    return (
        <div className="col-md-6">
            <label htmlFor={id} className="form-label">{label}</label>
            <input type="text" className="form-control" id={id} name={id} value={value}
                   onChange={onChange}/>
        </div>
    );
}



