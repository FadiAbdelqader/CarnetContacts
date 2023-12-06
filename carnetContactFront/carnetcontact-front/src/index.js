import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import Homepage from "./Homepage"
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

            </Routes>
        </AuthProvider>
    </BrowserRouter>
);

reportWebVitals();
