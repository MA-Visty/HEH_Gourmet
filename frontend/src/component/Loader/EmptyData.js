import React from "react";
import {Button} from "react-bootstrap";

function EmptyData() {
    return (
        <Button variant="secondary" disabled>
            Aucun résultat trouvé.
        </Button>
    );
}

export default EmptyData;