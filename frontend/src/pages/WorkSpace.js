import React from "react";
import {useAppContext} from "../store/AppContext";
import {Navigate} from 'react-router-dom';

function WorkSpace() {
    const { state } = useAppContext();

    if (state.user.role !== "worker") {
        // Redirige vers la page souhaitée (par exemple, '/autre-page')
        return <Navigate to="/" />;
    }

    return (<></>);
}

export default WorkSpace;