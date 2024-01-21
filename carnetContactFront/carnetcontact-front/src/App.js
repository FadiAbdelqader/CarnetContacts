import React, {useContext, useState} from 'react';
import "bootstrap/dist/css/bootstrap.min.css";
import logo from './carnetContactLogo.jpg';
import {useNavigate} from "react-router-dom";
import {AuthContext} from "./AuthContext";

export default function App() {
  return (
      <>
        <Connection />
      </>
  );
}

function Connection() {
  const navigate = useNavigate();
  const [username, setUsername] = useState("Enter your username");
  const [password, setPassword] = useState("Enter your Password");
  const context = useContext(AuthContext)
  const [errorMessage, setErrorMessage] = useState("");
  const handleChangeUsername = (event) => {
    setUsername(event.target.value);
    console.log("username : ",username)
  }

  const handleChangePassword = (event) => {
    setPassword(event.target.value);
  }

  const handleSubmit = (event) => {
    event.preventDefault();
    if (username !== password) {
      setErrorMessage("Username or password is incorrect");
    } else {
      context.setUser(username);
      navigate('/homepage');
    }
  }

  return (
      <div className="container mt-5">
        <div className="row justify-content-center">
          <div className="col-md-6">
            <div className="card">
              <div className="text-center">
                <img src={logo} alt="Logo" style={{ width: '350px', height: 'auto', margin: '20px 0' }} />
              </div>
              <h5 className="card-header text-center">Connection</h5>
              <div className="card-body">
                <form onSubmit={handleSubmit}>
                  {errorMessage && (
                      <div className="alert alert-danger" role="alert">
                        {errorMessage}
                      </div>
                  )}
                  <div className="form-group">
                    <label>Username</label>
                    <input type="text" className="form-control" id="username" aria-describedby="usernameHelp" placeholder={username} onChange={handleChangeUsername}/>
                  </div>
                  <div className="form-group">
                    <label htmlFor="password">Password</label>
                    <input type="password" className="form-control" id="password" placeholder={password} onChange={handleChangePassword}/>
                  </div>
                  <button type="submit" className="btn btn-primary btn-block">Conection</button>
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
  );
}