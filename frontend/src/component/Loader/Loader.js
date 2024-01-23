import React from "react";
import {Spinner, Button} from "react-bootstrap";

function Loader() {
    return (
        <Button variant="primary" disabled>
            <Spinner as="span" animation="border" size="sm" role="status" aria-hidden="true"/>
            {' '}
            Chargement...
        </Button>
    );
}

export default Loader;