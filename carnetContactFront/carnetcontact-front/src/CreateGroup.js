import {HomePage} from "./Mycontacts";
import React, {useEffect, useState} from "react";
import {HTML5Backend} from "react-dnd-html5-backend";
import {DndProvider, useDrag, useDrop} from "react-dnd";

export default function CreateGroup() {
    const [selectedGroup, setSelectedGroup] = useState(null);
    const [selectedContact, setSelectedContact] = useState([]);
    return (
        <>
            <DndProvider backend={HTML5Backend}>
                <HomePage/>
                <CreateGroupForm/>
                <div className="container mt-4">
                    <div className="row">
                        {/* Left Side: GroupsSummary and ContactsSummary */}
                        <div className="col-md-6">
                            <div className="row">
                                <div className="col-md-12 mb-4">
                                    <GroupsSummary setSelectedGroup={setSelectedGroup}/>
                                </div>
                                <div className="col-md-12">
                                    <ContactsSummary setSelectedContact={setSelectedContact}/>
                                </div>
                            </div>
                        </div>
                        {/* Right Side: AddContactWindow */}
                        <div className="col-md-6">
                            <AddContactWindow selectedGroup={selectedGroup} selectedContact={selectedContact}/>
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
        <div className="container mt-4">
            <table className="table table-bordered">
                <thead>
                <tr>
                    <th>Groups</th>
                </tr>
                </thead>
                <tbody>
                {groups.map((group) => (
                    <tr key={group.idGroup} onClick={() => handleGroupClick(group)}>
                        <td>{group.groupName}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}

function TD({contact, setSelectedContact}){
 const [selected, setSelected] = useState(false);
  const [{isDragging}, drag] = useDrag(() => ({
        type: "box",
        item: {},
        end: (item, monitor) => {
            setSelected(true)
            setSelectedContact((prevContact)=>[...prevContact, contact])
        },
        collect: (monitor) => ({
            isDragging: monitor.isDragging(),
            handlerId: monitor.getHandlerId(),
        }),
    }))
    return <td ref={selected?undefined:drag}>{contact.firstName} {contact.lastName}</td>
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
                        <TD setSelectedContact={setSelectedContact} contact={contact}>

                        </TD>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}

function AddContactWindow({selectedGroup, selectedContact}) {

    const [{ canDrop, isOver }, drop] = useDrop(() => ({
        accept: "box",
        drop: () => ({ name: 'AddContactWindow' }),
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
            const contactList = selectedContact.reduce((acc, contact)=>`${acc}&idContacts=${contact.idContact}`, "")
            const response = await fetch(`http://localhost:8080/addContact?idGroup=${selectedGroup.idGroup}${contactList}`, requestOptions);
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            console.log("ok!!!!!");

        } catch (error) {
            console.log("Error in submitting address:", error.message);
        }
    }

    return (
        <>
            <div className="d-flex justify-content-center align-items-center" style={{height: '100vh'}}>
                <div className="card border-primary-subtle mb-3"
                     style={{width: '70%', maxWidth: '120rem', height: '80%'}}>
                    <div className="card-header bg-transparent border-success">
                        <h2>{selectedGroup ? selectedGroup.groupName : "Select a group"}</h2>
                    </div>
                    <div ref={drop} className="card-body text-success">
                        <p>drag and drop contacts here</p>
                        <div className="container mt-4">
                            <table className="table table-bordered">
                                <thead>
                                <tr>
                                    <th>Contacts</th>
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
                    </div>
                    <button className="btn btn-primary" onClick={handleAddContactToGroup}>Send</button>
                </div>
            </div>
        </>
    );
}































