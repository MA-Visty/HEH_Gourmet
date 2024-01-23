import React, {useRef, useState} from 'react';
import {Offcanvas, Button, Row, Col, Form, ImputGroup, InputGroup} from 'react-bootstrap';
import {useAppContext, useDispatchContext} from "../../store/AppContext";
import axios from "axios";
import API_URL from "../../apiConfig";

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
    const password = useRef();
    const handleLogin = async (event) => {
        event.preventDefault();
        let emailCheck = email.current.value;
        let pwdCheck = password.current.value;

        if(emailCheck === "" || pwdCheck === "") {
            setValidated(true);
            return;
        } else {
            try {
                const response = await axios
                    .post(`${API_URL}/api/auth/login`, {
                        email: emailCheck,
                        password: pwdCheck
                    });
                dispatch({
                    type: "login",
                    response: response.data
                });
                switchLogAccount()
            } catch (except) {
                alert('nop');
            }
            return;
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
                            placeholder="Entrer your email"
                            defaultValue=""
                        />
                        <Form.Control.Feedback type="invalid">
                            Please enter a valide email.
                        </Form.Control.Feedback>
                    </InputGroup>
                </Form.Group>
                <Form.Group>
                    <Form.Label>Password</Form.Label>
                    <InputGroup hasValidation>
                        <InputGroup.Text id="inputGroupPrepend">#</InputGroup.Text>
                        <Form.Control
                            required
                            ref={password}
                            type="password"
                            placeholder="Entrer your password"
                            aria-describedby="passwordHelpBlock"
                        />
                        <Form.Control.Feedback type="invalid">
                            Please enter a valide password.
                        </Form.Control.Feedback>
                    </InputGroup>
                </Form.Group>
            </Row>

            <div className="d-grid gap-2">
                <Button variant="primary" size="lg" onClick={handleLogin}>Login</Button>
                <Button variant="secondary" size="lg" onClick={switchLogRegist}>Register</Button>
            </div>
        </Form>
    );
}

function UserRegister({switchLogRegist}) {
    const { dispatch } = useDispatchContext();
    const [validated, setValidated] = useState(false);
    const email = useRef();
    const password = useRef();
    const confirmpassword = useRef();
    const [invalid, setInvalid] = useState({})

    const handleRegister = async (event) => {
        event.preventDefault();
        let emailCheck = email.current.value;
        let pwd1Check = password.current.value;
        let pwd2Check = confirmpassword.current.value;

        if(emailCheck !== "" || pwd1Check !== "" || pwd2Check !== "" || pwd1Check !== pwd2Check) {
            setValidated(true);
            return;
        } else {
            try {
                const response = await axios
                    .post(`${API_URL}/api/auth/register`, {
                        name: "",
                        username: "",
                        email: emailCheck,
                        password: pwd1Check,
                        passwordConfirmation: pwd2Check,
                        role: "user",
                        phone: "",
                        address: "",
                        companyName: "",
                        image: ""
                    });
                setValidated(false);
            } catch (error) {
                console.error('Registration error:', error);
                alert('nop');
                if (error.response && error.response.status === 422) {
                    // La validation côté serveur a échoué
                    const errors = error.response.data.errors;
                    setInvalid({
                        email: errors && errors.email ? "Invalid email" : null,
                        pwd1: errors && errors.password ? "Invalid password" : null,
                        pwd2: errors && errors.passwordConfirmation ? "Invalid password confirmation" : null
                    });
                } else {
                    // Une autre erreur s'est produite (ex: serveur non disponible)
                    setInvalid({
                        email: "Registration failed. Please try again later.",
                        pwd1: null,
                        pwd2: null
                    });
                }
            }
            return;
        }
    }

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
                            placeholder="Entrer your email"
                            defaultValue=""
                            isInvalid={!!invalid.email}
                        />
                        <Form.Control.Feedback type="invalid">
                            {invalid.email}
                        </Form.Control.Feedback>
                    </InputGroup>
                </Form.Group>
                <Form.Group>
                    <Form.Label>Password</Form.Label>
                    <InputGroup hasValidation>
                        <InputGroup.Text id="inputGroupPrepend">#</InputGroup.Text>
                        <Form.Control
                            required
                            ref={password}
                            type="password"
                            placeholder="Entrer your password"
                            aria-describedby="passwordHelpBlock"
                            isInvalid={!!invalid.pwd1}
                        />
                    </InputGroup>
                </Form.Group>
                <Form.Group>
                    <InputGroup hasValidation>
                        <InputGroup.Text id="inputGroupPrepend">#</InputGroup.Text>
                        <Form.Control
                            required
                            ref={confirmpassword}
                            type="password"
                            placeholder="Confirm your password"
                            aria-describedby="passwordHelpBlock"
                            isInvalid={!!invalid.pwd2}
                        />
                        <Form.Control.Feedback type="invalid">
                            {invalid.pwd1}
                            {invalid.pwd2}
                        </Form.Control.Feedback>
                    </InputGroup>
                </Form.Group>
            </Row>

            <div className="d-grid gap-2">
                <Button variant="primary" size="lg" onClick={handleRegister}>Confirm</Button>
                <Button variant="secondary" size="lg" onClick={switchLogRegist}>Cancel</Button>
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