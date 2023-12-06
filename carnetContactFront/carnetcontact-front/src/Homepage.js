import React, {useContext} from "react";
import {AuthContext} from "./AuthContext";

export default function Homme() {
    return (
        <>
            <Hello />
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