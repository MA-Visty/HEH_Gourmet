import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import React, {useRef, useState} from "react";
import {Form, Image, InputGroup} from "react-bootstrap";
import Add from "../../../assets/images/add.svg";
import {useDispatchContext} from "../../../store/AppContext";
import axios from "axios";
import API_URL from "../../../apiConfig";
import emptyData from "../../Loader/EmptyData";
import {Navigate} from "react-router-dom";

function ButtonNewItemFilter() {
    const { dispatch } = useDispatchContext();
    const [validated, setValidated] = useState(false);
    const category = useRef("");
    const description = useRef("");
    const [invalid, setInvalid] = useState({})
    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    const handleRegister = async (event) => {
        if(category.current.value == "") {
            setValidated(true);
            return;
        } else {
            try {
                const response = await axios
                    .post(`${API_URL}/api/category`, {
                        name: category.current.value,
                        description: description.current.value
                    });
                setValidated(false);
            } catch (error) {
                setInvalid({
                    category: `Impossible d'ajouter la categorie : ${category}`
                });
            }
            handleClose()

            return <Navigate to="/Menu" />;
        }
    }

    return (
        <>
            <Button variant="outline-secondary" style={{backgroundImage:`url(${Add})`, backgroundSize:"cover", width:"40px", height:"40px"}} onClick={handleShow} />

            <Modal centered show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Ajouter une catégorie</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form noValidate validated={validated}>
                        <Form.Group>
                            <Form.Label>Catégorie</Form.Label>
                            <InputGroup hasValidation>
                                <Form.Control
                                    required
                                    ref={category}
                                    type="text"
                                    placeholder="Entrer un nom de catégorie"
                                    defaultValue=""
                                    isInvalid={!!invalid.category}
                                />
                                <Form.Control.Feedback type="invalid">
                                    {invalid.category}
                                </Form.Control.Feedback>
                            </InputGroup>
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="exampleForm.ControlTextarea1">
                            <Form.Label>Description</Form.Label>
                            <Form.Control
                                ref={description}
                                type="text"
                                placeholder="Entrer une description"
                                defaultValue=""
                                as="textarea"
                                rows={3} />
                        </Form.Group>
                    </Form>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleClose}>
                        Annuler
                    </Button>
                    <Button variant="primary" onClick={handleRegister}>
                        Ajouter
                    </Button>
                </Modal.Footer>
            </Modal>
        </>
    );
}

export default ButtonNewItemFilter;