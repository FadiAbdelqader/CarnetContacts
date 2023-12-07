import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import Homepage from "./Homepage"
import CreateContact from "./CreateContact"
import reportWebVitals from './reportWebVitals';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import {AuthProvider} from "./AuthContext";
const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <BrowserRouter>
        <AuthProvider>
            <Routes>
                <Route path='/' element = {<App/>}/>
                <Route path='/homepage' element = {<Homepage/>}/>
                <Route path='/createcontact' element = {<CreateContact/>}/>
            </Routes>
        </AuthProvider>
    </BrowserRouter>
);

reportWebVitals();
