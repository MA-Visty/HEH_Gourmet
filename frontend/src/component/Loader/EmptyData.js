import React from "react";
import {Button} from "react-bootstrap";

function EmptyData() {
    return (
        <Button variant="secondary" disabled>
            No results found.
        </Button>
    );
}

export default EmptyData;