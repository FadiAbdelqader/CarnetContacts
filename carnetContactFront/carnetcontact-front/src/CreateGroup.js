import {HomePage} from "./Mycontacts";
import {LogOut} from "./Homepage";
import React, {useEffect, useState} from "react";
import {HTML5Backend} from "react-dnd-html5-backend";
import {DndProvider, useDrag, useDrop} from "react-dnd";
import {useNavigate} from "react-router-dom";

export default function CreateGroup() {
    const [selectedGroup, setSelectedGroup] = useState(null);
    const [selectedContact, setSelectedContact] = useState([]);
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
            <DndProvider backend={HTML5Backend}>
                <CreateGroupForm />
                <div className="container-fluid mt-4">
                    <div className="row flex-nowrap">
                        <div className="col-md-6">
                            <GroupsSummary setSelectedGroup={setSelectedGroup} />
                            <ContactsSummary setSelectedContact={setSelectedContact} />
                        </div>
                        <div className="col-md-6">
                            <AddContactWindow selectedGroup={selectedGroup} selectedContact={selectedContact} />
                        </div>
                    </div>
                </div>
            </DndProvider>
        </>
    );
}

function CreateGroupForm() {
    const [state, setState] = useState({groupName: ""});

    const handleChangeGroupName = (event) => {
        const groupName = event.target.value;
        setState({...state, groupName: groupName});
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
        <div className="container my-4">

                <div className="card-header">
                    <h5>Create new group :</h5>
                </div>
                <div className="card-body">
                    <form onSubmit={handleSubmitCreateGroup} className="row g-3 align-items-center">
                        <div className="col-md-8">
                            <input type="text" id="groupName" name="groupName"
                                   className="form-control"
                                   value={state.groupName}
                                   onChange={handleChangeGroupName}
                                   placeholder="Enter group name" />
                        </div>
                        <div className="col-md-2">
                            <button type="submit" className="btn btn-primary">Create Group</button>
                        </div>
                    </form>
                </div>
            </div>

    );
}

function GroupsSummary({setSelectedGroup}) {
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

    const handleGroupClick = (group) => {
        setSelectedGroup(group);
    };

    return (
        <div className="card my-2">
            <div className="card-header py-2">
                <h5 className="mb-0">Groups</h5>
            </div>
            <div className="card-body p-2">
                <table className="table table-hover table-sm mb-0">
                    <thead>
                    <tr>
                        <th>Group Name</th>
                    </tr>
                    </thead>
                    <tbody>
                    {groups.map((group) => (
                        <tr key={group.idGroup} onClick={() => setSelectedGroup(group)}>
                            <td>{group.groupName}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
}

function TD({contact, setSelectedContact}) {
    const [selected, setSelected] = useState(false);
    const [{isDragging}, drag] = useDrag(() => ({
        type: "box",
        item: {},
        end: (item, monitor) => {
            setSelected(true)
            setSelectedContact((prevContact) => [...prevContact, contact])
        },
        collect: (monitor) => ({
            isDragging: monitor.isDragging(),
            handlerId: monitor.getHandlerId(),
        }),
    }))
    return <td ref={selected ? undefined : drag}>{contact.firstName} {contact.lastName}</td>
}

function ContactsSummary({setSelectedContact}) {
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
        <div className="card my-2">
            <div className="card-header py-2">
                <h5 className="mb-0">Contacts</h5>
            </div>
            <div className="card-body p-2">
                <table className="table table-hover table-sm mb-0">
                    <thead>
                    <tr>
                        <th>Contact Name</th>
                    </tr>
                    </thead>
                    <tbody>
                    {contacts.map((contact) => (
                        <tr key={contact.idContact}>
                            <TD setSelectedContact={setSelectedContact} contact={contact} />
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
}

function AddContactWindow({selectedGroup, selectedContact}) {
    const navigate = useNavigate();
    const [{canDrop, isOver}, drop] = useDrop(() => ({
        accept: "box",
        drop: () => ({name: 'AddContactWindow'}),
        collect: (monitor) => ({
            isOver: monitor.isOver(),
            canDrop: monitor.canDrop(),
        }),
    }))

    console.log("selectedGroup", selectedGroup)

    async function handleAddContactToGroup(event) {
        const requestOptions = {
            method: "POST",
            headers: {"Content-Type": "application/json"},
        };

        try {
            const contactList = selectedContact.reduce((acc, contact) => `${acc}&idContacts=${contact.idContact}`, "")
            const response = await fetch(`http://localhost:8080/addContact?idGroup=${selectedGroup.idGroup}${contactList}`, requestOptions);
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            console.log("ok!!!!!");
            navigate('/myGroups')
        } catch (error) {
            console.log("Error in submitting address:", error.message);
        }
    }

    return (
        <>
            <div className="card my-2">
                <div className="card-header py-2">
                    <h5>{selectedGroup ? selectedGroup.groupName : "Select a Group"}</h5>
                </div>
                <div ref={drop} className="card-body text-success p-2" style={{ minHeight: '33.3rem' }}> {/* Set a minimum height */}
                    <div className="container mt-4">
                        <table className="table table-bordered">
                            <thead>
                            <tr>
                                <th>Drag and drop contacts here</th>
                            </tr>
                            </thead>
                            <tbody>
                            {selectedContact.map((contact) => (
                                <tr key={contact.idContact}>
                                    <td>
                                        {contact.firstName} {contact.lastName}
                                    </td>
                                </tr>
                            ))}
                            </tbody>
                        </table>
                    </div>
                    <div className="card-footer text-center">
                        <button className="btn btn-primary" onClick={handleAddContactToGroup}>Add Contacts to Group
                        </button>
                    </div>
                </div>
            </div>
        </>
    );
}













































