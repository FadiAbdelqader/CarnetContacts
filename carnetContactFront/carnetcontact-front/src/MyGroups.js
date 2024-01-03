import React, {useEffect, useState} from "react";
import {HomePage} from "./Mycontacts";
import {LogOut} from "./Homepage";
export default function MyContacts() {
    const [selectedGroupId, setSelectedGroupId] = useState(null);
    const [groupToUpdate, setGroupToUpdate] = useState(null);

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
            <GroupsSummary onGroupSelect={setSelectedGroupId} onUpdateGroupSelect={setGroupToUpdate} />
            <GroupWindow selectedGroupId={selectedGroupId} />
            {groupToUpdate && <UpdateGroupWindow groupInfo={groupToUpdate} />}
        </>
    );
}

function GroupsSummary({onGroupSelect, onUpdateGroupSelect}) {
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
    async function deleteGroup(idGroup) {
        const option = {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
        };

        try {
            const response = await fetch(`http://localhost:8080/deletegroupbyid?idGroup=${idGroup}`, option);
            if (response.ok) {
                setGroups(groups.filter(group => group.idGroup !== idGroup));
            } else {
                console.error('Suppression du group : ERREUR!');
            }
        } catch (error) {
            console.error(error.message);
        }
    }

    return (
        <div className="container mt-4">
            <h2 className="mb-3">Your groups :</h2>
            <table className="table table-hover">
                <thead className="table-dark">
                <tr>
                    <th>Groups</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                    {groups.map((group) => (
                        <tr key={group.idGroup}>
                            <td onClick={() => onGroupSelect(group.idGroup)}>{group.groupName}</td>
                            <td>
                                <button className="btn btn-danger me-2" onClick={() => deleteGroup(group.idGroup)}>
                                    Delete
                                </button>
                                <button className="btn btn-primary" onClick={() => onUpdateGroupSelect(group)}>
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
function GroupWindow({ selectedGroupId }) {
    const [groupInfo, setGroupInfo] = useState(null);

    useEffect(() => {
        if (selectedGroupId) {
            getGroupInfo(selectedGroupId);
        }
    }, [selectedGroupId]);

    async function getGroupInfo(groupId) {
        const option = {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        };

        try {
            const response = await fetch(`http://localhost:8080/getContactsByGroupId?groupId=${groupId}`, option);
            if (response.ok) {
                const data = await response.json();
                if (data.length > 0) {
                    setGroupInfo(data);
                } else {
                    setGroupInfo(null);
                }
            } else {
                console.error('Récupération des infos du groupe : ERREUR!');
            }
        } catch (error) {
            console.error(error.message);
        }
    }

    if (!groupInfo) {
        return <div>Select a group to see its contacts</div>;
    }

    return (
        <div className="card mx-3 my-4">
            <div className="card-body">
                <h5 className="card-title">Group {groupInfo.groupName} Details</h5>
                {groupInfo.map((contact, index) => (
                    <div key={index}>
                        <h6>Contact {index + 1}:</h6>
                        <p><strong>Identity :</strong> {contact.firstName} {contact.lastName} </p>
                        <p><strong>Contact :</strong> {contact.email} or on his/her {contact.phoneKind} at {contact.phoneNumber}</p>
                        <p><strong>Address :</strong> {contact.number}  {contact.street} in {contact.city}, {contact.zip} {contact.country}</p>
                    </div>
                ))}
            </div>
        </div>
    );
}

function UpdateGroupWindow({ groupInfo }) {
    const [groupName, setGroupName] = useState('');

    const handleGroupNameChange = (event) => {
        setGroupName(event.target.value);
    };

    const SaveNewGroupName = async (event) => {
        event.preventDefault();

        const requestOptions = {
            method: 'PUT',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({"idGroup": groupInfo.idGroup, "groupName": groupName})
        };

        try {
            const response = await fetch('http://localhost:8080/updategroup', requestOptions);
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            console.log("okay pour idgroup", groupInfo.idGroup);
            window.location.reload();
        } catch (error) {
            console.error("Error in updating contact:", error.message);
        }
    };

    return (
        <div className="card mx-3 my-4">
            <div className="card-body">
                <h5 className="card-title">Update Contact</h5>
                <form onSubmit={SaveNewGroupName} className="row g-3">
                    {createInputField("groupName", "group name", groupName, handleGroupNameChange)}
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
