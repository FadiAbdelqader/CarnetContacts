import React, {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";

export default function MyContacts() {
    const [selectedContactId, setSelectedContactId] = useState(null);

    return (
        <>
            <HomePage />
            <ContactsSummary onContactSelect={setSelectedContactId} />
            <MessageWindow selectedContactId={selectedContactId} />
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

function ContactsSummary({ onContactSelect }) {
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

    return (
        <div className="container mt-4">
            <table className="table table-bordered">
                <thead>
                <tr>
                    <th>Contacts</th>
                </tr>
                </thead>
                <tbody>
                {contacts.map((contact) => (
                    <tr key={contact.idContact} onClick={() => onContactSelect(contact.idContact)}>
                        <td>{contact.firstName} {contact.lastName}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}

function MessageWindow({ selectedContactId }) {
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
        return <div>Loading...</div>;
    }

    return (
        <div className="card">
            <div className="card-body">
                <h5 className="card-title">Contact Details</h5>
                <p className="card-text"><strong>First Name:</strong> {contactInfo.firstName}</p>
                <p className="card-text"><strong>Last Name:</strong> {contactInfo.lastName}</p>
                <p className="card-text"><strong>Email:</strong> {contactInfo.email}</p>
                <p className="card-text"><strong>Number:</strong> {contactInfo.number}</p>
                <p className="card-text"><strong>Street:</strong> {contactInfo.street}</p>
                <p className="card-text"><strong>City:</strong> {contactInfo.city}</p>
                <p className="card-text"><strong>ZIP:</strong> {contactInfo.zip}</p>
                <p className="card-text"><strong>Country:</strong> {contactInfo.country}</p>
                <p className="card-text"><strong>Phone Kind:</strong> {contactInfo.phoneKind}</p>
                <p className="card-text"><strong>Phone Number:</strong> {contactInfo.phoneNumber}</p>
            </div>
        </div>
    );
}