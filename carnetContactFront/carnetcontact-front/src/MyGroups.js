import React, {useEffect, useState} from "react";
import {HomePage} from "./Mycontacts";
import {useNavigate} from "react-router-dom";

export default function MyContacts() {
    const [selectedGroupId, setSelectedGroupId] = useState(null);
    const [groupToUpdate, setGroupToUpdate] = useState(null);

    return (
        <>
            <HomePage />
            <GroupsSummary onGroupSelect={setSelectedGroupId} onUpdateGroupSelect={setGroupToUpdate}/>
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
                            <td>{group.groupName}</td>
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
