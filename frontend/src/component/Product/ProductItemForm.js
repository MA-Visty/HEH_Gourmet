import React, {useRef, useState} from "react";
import { Link } from "react-router-dom";
import {Row, Col, Button, InputGroup, Form, Stack} from 'react-bootstrap';
import { useDispatchContext } from "../../store/AppContext";
import MenuImage from "../../assets/images/menu.svg";

function ProductItemForm({product}) {
    const { dispatch } = useDispatchContext();
    const quantity = useRef();
    const [invalid, setInvalid] = useState(false);
    const AddCart = (element) => {
        let num = quantity.current.value;
        if (num === null || !Number.isInteger(parseInt(num)) || num < 1 || Number.isNaN(parseInt(num))) {
            setInvalid(true);
            return;
        } else {
            setInvalid(false);
        }
        dispatch({
            type: "add",
            product: product,
            quantity: Number(quantity.current.value),
            color: "red",
            size: "M",
        });
        quantity.current.value = 0
    };

	return (
        <Stack direction="horizontal" gap={2}>
            <Button variant="outline-secondary" as={Link} to={`/product/${product.id}`}>
                <img style={{objectFit:'cover', display:"flex", height:"1.5rem", width:"auto"}} src={MenuImage} alt="error" />
            </Button>
            <InputGroup>
                <Form.Control ref={quantity} required isInvalid={invalid} type="number" min="0" max={product.quantity} aria-label="Recipient's username" aria-describedby="basic-addon2" />
                <Button style={{width: "50%"}} variant="primary" id="button-addon2" onClick={AddCart}>Add</Button>
            </InputGroup>
        </Stack>
    );
  }
  
  export default ProductItemForm;