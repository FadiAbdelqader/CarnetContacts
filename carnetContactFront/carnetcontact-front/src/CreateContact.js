import React, {useState} from "react";

export default function Home() {
    return (
        <>
            <CreateContactForm/>
        </>
    );
}

function CreateContactForm() {
    const inputStyle = { border: '3px solid', padding: '0.375rem 0.75rem' };

    const [state, setState] = useState({});

    const handleChangeStreetNumber = (event) => {
        const number = event.target.value;
        setState({...state, number: number});
        console.log("nmb : ", number)
    }

    const handleChangeStreet = (event) => {
        const street = event.target.value;
        setState({...state, street: street});
    }
    const handleChangeCity = (event) => {
        const city = event.target.value;
        setState({...state, city: city});
    }
    const handleChangeZip = (event) => {
        const zip = event.target.value;
        setState({...state, zip: zip});
    }
    const handleChangeCountry = (event) => {
        const country = event.target.value;
        setState({...state, country: country});
    }

    const handleSubmitAddress = (event) => {
        event.preventDefault();

        const userData = {
            number: state.number,
            street: state.street,
            city: state.city,
            zip: state.zip,
            country: state.country
        };


        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(userData)
        };
        fetch('http://localhost:8080/createaddress', requestOptions)
            .then(response => console.log("ok"))
            .catch(error => console.log(error.message))
    }

    return (
        <>
            <div className="container">
                <h1 className="text-center my-4">Add a contact to your contact book</h1>
                <form onSubmit={handleSubmitAddress}>
                    <div className="row">
                        <div className="col-md-4 mb-3">
                            <label htmlFor="number">Number</label>
                            <input type="text" className="form-control" id="number" name="number" value={state.number} placeholder="Enter number" onChange={handleChangeStreetNumber} style={inputStyle} />
                        </div>
                        <div className="col-md-4 mb-3">
                            <label htmlFor="street">Street</label>
                            <input type="text" className="form-control" id="street" name="street" value={state.street} placeholder="Enter street" onChange={handleChangeStreet} style={inputStyle} />
                        </div>
                        <div className="col-md-4 mb-3">
                            <label htmlFor="city">City</label>
                            <input type="text" className="form-control" id="city" name="city" value={state.city} placeholder="Enter city" onChange={handleChangeCity} style={inputStyle} />
                        </div>
                        <div className="col-md-4 mb-3">
                            <label htmlFor="zip">ZIP Code</label>
                            <input type="text" className="form-control" id="zip" name="zip" value={state.zip} placeholder="Enter ZIP code" onChange={handleChangeZip} style={inputStyle} />
                        </div>
                        <div className="col-md-4 mb-3">
                            <label htmlFor="country">Country</label>
                            <input type="text" className="form-control" id="country" name="country" value={state.country} placeholder="Enter country" onChange={handleChangeCountry} style={inputStyle} />
                        </div>
                    </div>

                    <div className="row">
                        <div className="col-md-4 mb-3">
                            <label htmlFor="firstName">firstName</label>
                            <input type="text" className="form-control" id="firstName" name="firstName" value={state.number} placeholder="Enter your firstname"  style={inputStyle} />
                        </div>
                        <div className="col-md-4 mb-3">
                            <label htmlFor="lastName">lastname</label>
                            <input type="text" className="form-control" id="lastName" name="lastName" value={state.street} placeholder="Enter your lastname" style={inputStyle} />
                        </div>
                        <div className="col-md-4 mb-3">
                            <label htmlFor="email">E-mail</label>
                            <input type="text" className="form-control" id="email" name="email" value={state.city} placeholder="Enter your E-mail" onChange={handleChangeCity} style={inputStyle} />
                        </div>
                    </div>
































                    <div className="text-center">
                        <button type="submit" className="btn btn-primary btn-lg mt-2" >Add Contact</button>
                    </div>
                </form>
            </div>
        </>
    );
}



