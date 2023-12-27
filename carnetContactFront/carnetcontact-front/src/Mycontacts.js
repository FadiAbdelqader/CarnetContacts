import React, {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";

export default function MyContacts() {
    const [selectedContactId, setSelectedContactId] = useState(null);
    const [contactToUpdate, setContactToUpdate] = useState(null);

    return (
        <>
            <HomePage/>
            <ContactsSummary onContactSelect={setSelectedContactId} onUpdateContactSelect={setContactToUpdate}/>
            <ContactWindow selectedContactId={selectedContactId} />
            {contactToUpdate && <UpdateContactWindow contactInfo={contactToUpdate} />}
        </>
    );
}

export function HomePage() {
    const navigate = useNavigate();

    function handleClickButton() {
        navigate('/homepage');
    }

    return (
        <>
            <button className="btn btn-primary" onClick={handleClickButton}>Home</button>
        </>
    );
}

function ContactsSummary({ onContactSelect, onUpdateContactSelect }) {
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
            <table className="table table-bordered">
                <thead>
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
                            <button className="btn btn-danger" onClick={() => deleteContacts(contact.idContact)}>
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

function ContactWindow({ selectedContactId }) {
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
        <div className="card">
            <div className="card-body">
                <h5 className="card-title">Contact Details : </h5>
                <p className="card-text"><strong>First Name:</strong> {contactInfo.firstName}</p>
                <p className="card-text"><strong>Last Name:</strong> {contactInfo.lastName}</p>
                <p className="card-text"><strong>Email:</strong> {contactInfo.email}</p>
                <p className="card-text"><strong>Address:</strong> {contactInfo.number}, {contactInfo.street} in {contactInfo.city} {contactInfo.zip} {contactInfo.country}</p>
                <p className="card-text"><strong>Phone Kind:</strong> {contactInfo.phoneKind}</p>
                <p className="card-text"><strong>Phone Number:</strong> {contactInfo.phoneNumber}</p>
            </div>
        </div>
    );
}

function UpdateContactWindow({ contactInfo }) {
    // Décomposition de contactInfo pour faciliter l'accès aux champs
    const { firstName, lastName, email, number, street, city, zip, country, phoneKind, phoneNumber } = contactInfo;

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
        const { name, value } = event.target;
        setState({ ...state, [name]: value });
    };

    async function handleSubmitContact(event) {
        event.preventDefault();
        const userData = {
            ...state,
            idContact: contactInfo.idContact
        };

        const requestOptions = {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
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
        }
    }

    return (
        <div className="card">
            <div className="card-body">
                <h5 className="card-title">Update {state.firstName} {state.lastName} :</h5>
                <form onSubmit={handleSubmitContact}>
                    <div>
                        <strong>First Name:</strong>
                        <input type="text" name="firstName" value={state.firstName} onChange={handleChange} />
                    </div>
                    <div>
                        <strong>Last Name:</strong>
                        <input type="text" name="lastName" value={state.lastName} onChange={handleChange} />
                    </div>
                    <div>
                        <strong>Email:</strong>
                        <input type="text" name="email" value={state.email} onChange={handleChange} />
                    </div>
                    <div>
                        <strong>Number:</strong>
                        <input type="text" name="number" value={state.number} onChange={handleChange} />
                    </div>
                    <div>
                        <strong>Street:</strong>
                        <input type="text" name="street" value={state.street} onChange={handleChange} />
                    </div>
                    <div>
                        <strong>City:</strong>
                        <input type="text" name="city" value={state.city} onChange={handleChange} />
                    </div>
                    <div>
                        <strong>ZIP:</strong>
                        <input type="text" name="zip" value={state.zip} onChange={handleChange} />
                    </div>
                    <div>
                        <strong>Country:</strong>
                        <input type="text" name="country" value={state.country} onChange={handleChange} />
                    </div>
                    <div>
                        <strong>Phone Kind:</strong>
                        <input type="text" name="phoneKind" value={state.phoneKind} onChange={handleChange} />
                    </div>
                    <div>
                        <strong>Phone Number:</strong>
                        <input type="text" name="phoneNumber" value={state.phoneNumber} onChange={handleChange} />
                    </div>
                    <button type="submit">Save</button>
                </form>
            </div>
        </div>
    );
}




