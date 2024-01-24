import React, {useRef, useState} from 'react';
import {Offcanvas, Button, Row, Col, Form, ImputGroup, InputGroup} from 'react-bootstrap';
import {useAppContext, useDispatchContext} from "../../store/AppContext";
import axios from "axios";
import API_URL from "../../apiConfig";
import {Navigate} from "react-router-dom";
import {pushCart} from "../../store/CartContext";

// TODO: French UI
// TODO: Simplify code

function OffCanvasUser({ show, onHide, type, ...props }) {
    const { state } = useAppContext();
    const [title, setTitle] = useState(state.user !== "" ? "Account" : "Login");
    const switchLogRegist = () => {
        setTitle((title) => (title === 'Login' ? 'Register' : 'Login'));
    };
    const switchLogAccount = () => {

        setTitle((title) => (title === 'Login' ? 'Account' : 'Login'));
    };

    return (
        <Offcanvas show={show} onHide={onHide} placement={'end'} {...props}>
            <Offcanvas.Header closeButton>
                <Offcanvas.Title>{title}</Offcanvas.Title>
            </Offcanvas.Header>
            <Offcanvas.Body>
            {title === "Account" ? <UserAccount switchLogAccount={switchLogAccount}/>
                : title === "Register" ? <UserRegister switchLogRegist={switchLogRegist}/>
                : <UserLogin switchLogRegist={switchLogRegist} switchLogAccount={switchLogAccount}/>
            }
            </Offcanvas.Body>
        </Offcanvas>
    )
}

function UserLogin({switchLogRegist, switchLogAccount}) {
    const { dispatch } = useDispatchContext();
    const [validated, setValidated] = useState(false);
    const email = useRef();
    const handleLogin = async (event) => {
        event.preventDefault();
        let emailCheck = email.current.value;

        if(emailCheck === "") {
            setValidated(true);
            return;
        }
        try {
            const response = await axios
                .post(`${API_URL}/api/user/login`, {
                    email: emailCheck
                });
            if(response.status === 200) {
                const responseData = await axios
                    .get(`${API_URL}/api/cart/${response.data.id}`)
                    .then((response) => {
                        return response.data;
                    })
                    .catch((error) => {
                        console.log(error)
                    });

                dispatch({
                    type: "login",
                    response: {
                        user: response,
                        data: responseData
                    }
                });
                switchLogAccount()
            }
        } catch (error) {
            console.log(error)
        }
    };

    return (
        <Form noValidate validated={validated}>
            <Row className="mb-3">
                <Form.Group>
                    <Form.Label>Email</Form.Label>
                    <InputGroup hasValidation>
                        <InputGroup.Text id="inputGroupPrepend">@</InputGroup.Text>
                        <Form.Control
                            required
                            ref={email}
                            type="text"
                            placeholder="Entrer votre email"
                            defaultValue=""
                        />
                        <Form.Control.Feedback type="invalid">
                            Please enter a valide email.
                        </Form.Control.Feedback>
                    </InputGroup>
                </Form.Group>
            </Row>

            <div className="d-grid gap-2">
                <Button variant="primary" size="lg" onClick={handleLogin}>Connexion</Button>
                <Button variant="secondary" size="lg" onClick={switchLogRegist}>Créer un compte</Button>
            </div>
        </Form>
    );
}

function UserRegister({switchLogRegist}) {
    const { dispatch } = useDispatchContext();
    const [validated, setValidated] = useState(false);
    const lastname = useRef();
    const firstname = useRef();
    const email = useRef();
    const [invalid, setInvalid] = useState({})

    const handleRegister = async (event) => {
        event.preventDefault();
        let lastnameCheck = lastname.current.value;
        let firstnameCheck = firstname.current.value;
        let emailCheck = email.current.value;

        if(emailCheck === "" || lastnameCheck === "" || firstnameCheck === "") {
            setValidated(true);
            return;
        }

        try {
            const response = await axios
                .post(`${API_URL}/api/user/register`, {
                    lastname: lastnameCheck,
                    firstname: firstnameCheck,
                    email: emailCheck
                });
            setValidated(false);
            if(response.status === 201) {
                switchLogRegist()
            }
        } catch (error) {
            if (error.response && error.response.status === 409) {
                const errors = error.response.data.errors;
                setInvalid({
                    lastname: "USER_ALREADY_EXIST",
                    firstname: "USER_ALREADY_EXIST",
                    email: "USER_ALREADY_EXIST"
                });
            } else {
                setInvalid({
                    email: "Registration failed. Please try again later.",
                    pwd1: null,
                    pwd2: null
                });
            }
        }
    }

    return (
        <Form noValidate validated={validated}>
            <Row className="mb-3">
                <Form.Group>
                    <Form.Label>Nom</Form.Label>
                    <InputGroup hasValidation>
                        <InputGroup.Text id="inputGroupPrepend">#</InputGroup.Text>
                        <Form.Control
                            required
                            ref={lastname}
                            type="text"
                            placeholder="Entrer votre nom"
                            defaultValue=""
                            isInvalid={!!invalid.lastname}
                        />
                        <Form.Control.Feedback type="invalid">
                            {invalid.lastname}
                        </Form.Control.Feedback>
                    </InputGroup>
                </Form.Group>
                <Form.Group>
                    <Form.Label>Prénom</Form.Label>
                    <InputGroup hasValidation>
                        <InputGroup.Text id="inputGroupPrepend">#</InputGroup.Text>
                        <Form.Control
                            required
                            ref={firstname}
                            type="text"
                            placeholder="Entrer votre prénom"
                            defaultValue=""
                            isInvalid={!!invalid.firstname}
                        />
                        <Form.Control.Feedback type="invalid">
                            {invalid.firstname}
                        </Form.Control.Feedback>
                    </InputGroup>
                </Form.Group>
                <Form.Group>
                    <Form.Label>Email</Form.Label>
                    <InputGroup hasValidation>
                        <InputGroup.Text id="inputGroupPrepend">@</InputGroup.Text>
                        <Form.Control
                            required
                            ref={email}
                            type="text"
                            placeholder="Entrer votre email"
                            defaultValue=""
                            isInvalid={!!invalid.email}
                        />
                        <Form.Control.Feedback type="invalid">
                            {invalid.email}
                        </Form.Control.Feedback>
                    </InputGroup>
                </Form.Group>
            </Row>

            <div className="d-grid gap-2">
                <Button variant="primary" size="lg" onClick={handleRegister}>Confirmer</Button>
                <Button variant="secondary" size="lg" onClick={switchLogRegist}>Annuler</Button>
            </div>
        </Form>
    );
}

function UserAccount({switchLogAccount}) {
    const { dispatch } = useDispatchContext();
    const handleLogout = (event) => {
        dispatch({
            type: "logout"
        });
        switchLogAccount()
    }

    return (
        <Button onClick={handleLogout}>
            Logout
        </Button>
    );
}

export default OffCanvasUser;