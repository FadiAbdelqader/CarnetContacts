import React, {useContext} from "react";
import {AuthContext} from "./AuthContext";
import {useNavigate} from "react-router-dom";

export default function Home() {
    return (
        <>
            <Navbar />
            <Hello />
            <MyContacts />
            <AddContacts />
            <MyGroups />
            <AddGroup/>
        </>
    );
}

function Navbar() {
    return (
        <>
            <nav className="navbar navbar-light" style={{ backgroundColor: 'blueviolet' }}>
                <h1>MON CARNET CONTACT</h1>
            </nav>
         </>
    );
}

function Hello(){
    const {user} = useContext(AuthContext);
    return(
        <>
        <h1> hello {user}</h1>
        </>
    )
}

function MyContacts(){
    const navigate = useNavigate();
    function HandlclickButton(){
        navigate('/myContact')
    }
    return(
        <>
            <button className="btn btn-primary" onClick={HandlclickButton}> My contacts </button>
        </>
    )
}

function AddContacts(){
    const navigate = useNavigate();
    function HandlclickButton(){
        navigate('/createcontact')
    }
    return(
        <>
            <button className="btn btn-primary" onClick={HandlclickButton}> Add contacts </button>
        </>
    )
}

function MyGroups(){
    return(
        <>
            <button className="btn btn-primary"> My Groups </button>
        </>
    )
}

function AddGroup(){
    return(
        <>
            <button className="btn btn-primary"> Add Groups </button>
        </>
    )
}