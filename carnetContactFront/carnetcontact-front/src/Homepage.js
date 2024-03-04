import React, {useContext} from "react";
import {AuthContext} from "./AuthContext";
import {useNavigate} from "react-router-dom";
import 'bootstrap/dist/css/bootstrap.min.css';

export default function Home() {
    return (
        <>
            <LogOut/>
            <div className="container mt-5">
                <Navbar/>
                <Hello/>
                <div className="row mt-4 g-3">
                    <MyContacts/>
                    <AddContacts/>
                    <MyGroups/>
                    <AddGroup/>
                </div>
            </div>
        </>
    );
}

function Navbar() {
    return (
        <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
            <div className="container-fluid">
                <a className="navbar-brand fw-bold text-uppercase" href="#">Mon Carnet Contact</a>
            </div>
        </nav>
    );
}

function Hello() {
    const {user} = useContext(AuthContext);
    return (
        <div className="alert alert-primary mt-4" role="alert">
            <h4 className="alert-heading">Welcome, {user}!</h4>
            <p>Manage your contacts and groups efficiently.</p>
        </div>
    )
}

export function LogOut() {
    const navigate = useNavigate();

    function HandlclickButton() {
        navigate('/')
    }

    return (
        <div className="col-md-6 col-lg-3">
            <button className="btn btn-danger w-5" onClick={HandlclickButton}>Log out</button>
        </div>
    )
}

function MyContacts() {
    const navigate = useNavigate();

    function HandlclickButton() {
        navigate('/myContact')
    }

    return (
        <div className="col-md-6 col-lg-3">
            <button className="btn btn-outline-primary w-100" onClick={HandlclickButton}>My contacts</button>
        </div>
    )
}

function AddContacts() {
    const navigate = useNavigate();





























    function HandlclickButton() {
        navigate('/createcontact')
    }

    return (
        <div className="col-md-6 col-lg-3">
            <button className="btn btn-outline-danger w-100" onClick={HandlclickButton}>Add contacts</button>
        </div>
    )
}

function MyGroups() {
    const navigate = useNavigate();

    function HandlclickButton() {
        navigate('/myGroups')
    }

    return (
        <div className="col-md-6 col-lg-3">
            <button className="btn btn-outline-success w-100" onClick={HandlclickButton}>My groups</button>
        </div>
    )
}

function AddGroup() {
    const navigate = useNavigate();

    function HandlclickButton() {
        navigate('/createGroup')
    }

    return (
        <div className="col-md-6 col-lg-3">
            <button className="btn btn-outline-info w-100" onClick={HandlclickButton}>Manage groups</button>
        </div>
    )
}
