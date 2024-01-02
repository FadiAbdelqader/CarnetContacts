import {HomePage} from "./Mycontacts";
import React, {useEffect, useState} from "react";

export default function CreateGroup() {
    return (
        <>
            <HomePage />
            <div className="container mt-4">
                <div className="row">
                    <div className="col-lg-6">
                        <CreateGroupForm />
                    </div>
                    <div className="col-lg-6">
                        <div className="card">
                            <div className="card-body">
                                <h5 className="card-title">Contacts Summary</h5>
                                <ContactsSummary />
                            </div>
                        </div>
                    </div>
                </div>
                <div className="row mt-4">
                    <div className="col-lg-6">
                        <div className="card">
                            <div className="card-body">
                                <h5 className="card-title">Groups Summary</h5>
                                <GroupsSummary />
                            </div>
                        </div>
                    </div>
                    <div className="col-lg-6">
                        <div className="card">
                            <div className="card-body">
                                <h5 className="card-title">Add Contact Window</h5>
                                <AddContactWindow />
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </>
    );
}
function CreateGroupForm() {
    const [state, setState] = useState({groupName: ""});

    const handleChangeGroupName = (event) => {
        const groupName = event.target.value;
        setState({...state, groupName: groupName}); // Correction de la clé pour correspondre à la casse
    };

    async function handleSubmitCreateGroup(event) {
        event.preventDefault();

        const groupData = {
            groupName: state.groupName,
        };
        console.log("groupName", state.groupName);
        const requestOptions = {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(groupData),
        };

        try {
            const response = await fetch("http://localhost:8080/createGroup", requestOptions);
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            const idGroup = await response.json();
            console.log("Group created with ID:", idGroup);
            window.location.reload();
            return idGroup; // Retourne l'ID
        } catch (error) {
            console.log("Error in submitting address:", error.message);
            return null; // Retourne null en cas d'erreur
        }
    }

    return (
        <>
            <div>
                <form onSubmit={handleSubmitCreateGroup}>
                    <strong>Group Name:</strong>
                    <input type="text" name="groupName" value={state.groupName} onChange={handleChangeGroupName}/>
                    <button type="submit" className="btn btn-primary">
                        Create group
                    </button>
                </form>
            </div>
        </>
    );
}
function GroupsSummary() {
    const [groups, setGroups] = useState([]);

    useEffect(() => {
        getMyGroups();
    }, []);

    async function getMyGroups() {
        const option = {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        };

        try {
            const response = await fetch('http://localhost:8080/getAllGroups', option);
            if (response.ok) {
                const data = await response.json();
                setGroups(data);
            } else {
                console.error('Récupération des groups : ERREUR!');
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
                setGroups(groups.filter(contact => contact.idContact !== idContact));
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
                    <th>Groups</th>
                </tr>
                </thead>
                <tbody>
                {groups.map((group) => (
                    <tr key={group.idContact}>
                        <td>
                            {group.groupName}
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}
function ContactsSummary() {
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
                </tr>
                </thead>
                <tbody>
                {contacts.map((contact) => (
                    <tr key={contact.idContact}>
                        <td>
                            {contact.firstName} {contact.lastName}
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}
function AddContactWindow() {

    return (
        <>
            <div className="d-flex justify-content-center align-items-center" style={{height: '100vh'}}>
                <div className="card border-primary-subtle mb-3"
                     style={{width: '70%', maxWidth: '120rem', height: '80%'}}>
                    <div className="card-header bg-transparent border-success">
                        <h2>Group name</h2>
                    </div>
                    <div className="card-body text-success">
                        <p>drag and drop contacts here</p>
                    </div>
                    <button className="btn btn-primary">Send</button>
                </div>
            </div>
        </>
    );
}