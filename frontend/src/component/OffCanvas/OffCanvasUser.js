import React, {useRef, useState} from 'react';
import {Offcanvas, Button, Row, Col, Form, ImputGroup, InputGroup} from 'react-bootstrap';
import {useDispatchContext} from "../../store/AppContext";
import axios from "axios";

function OffCanvasUser({ show, onHide, type, ...props }) {
    const [title, setTitle] = useState("Login");

    if (type === "account" ) {
        setTitle("Account");
    } else if (type === "register") {
        setTitle("Register");
    }

    const changeCanvas = () => {
        setTitle((title) => (title === 'Login' ? 'Register' : 'Login'));
    };

    return (
        <Offcanvas show={show} onHide={onHide} placement={'end'} {...props}>
            <Offcanvas.Header closeButton>
                <Offcanvas.Title>{title}</Offcanvas.Title>
            </Offcanvas.Header>
            <Offcanvas.Body>
            {title === "Account" ? <UserAccount/>
                : title === "Register" ? <UserRegister changeCanvas={changeCanvas}/>
                : <UserLogin changeCanvas={changeCanvas}/>
            }
            </Offcanvas.Body>
        </Offcanvas>
    )
}

function UserLogin({changeCanvas}) {
    const { dispatch } = useDispatchContext();
    const [validated, setValidated] = useState(false);
    const email = useRef();
    const password = useRef();

    //const [user, setUser] = useState("");
    const handleLogin = async (event) => {
        event.preventDefault();
        let emailCheck = email.current.value;
        let pwdCheck = password.current.value;

        if(emailCheck === "" || pwdCheck === "") {
            setValidated(true);
            return;
        } else {
            /*
            dispatch
            */

            try {
                const response = await axios
                    .post("http://localhost:3000/api/auth/login", {
                        email: emailCheck,
                        password: pwdCheck
                    });
                //setUser("");
                console.log(response);
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
                <Button variant="secondary" size="lg" onClick={changeCanvas}>Register</Button>
            </div>
        </Form>
    );
}

function UserRegister({changeCanvas}) {
    const [validated, setValidated] = useState(false);
    const email = useRef();
    const password = useRef();
    const confirmpassword = useRef();

    const labelFormGroup = [
        [email, "email", "Email", ""],
        [password, "password", "Password", ""],
        [confirmpassword, "password", "Confirm password", "Your password must be 8-20 characters long, contain letters and numbers,\n" +
        "                                    and must not contain spaces, special characters, or emoji."]
    ]

    return (
        <Form noValidate validated={validated}>
            <Row className="mb-3">
                {labelFormGroup.map(([ref, type, label, describe]) => (
                    <Form.Group key={label}>
                        <Form.Label>{label}</Form.Label>
                        <Form.Control
                            required
                            ref={ref}
                            type={type}
                            placeholder={`Enter your ${label}`}
                            defaultValue=""
                            aria-describedby={describe !== "" ? `{$label}HelpBlock` : ""}
                        />
                        {describe !== "" ?
                            <Form.Text id={`${label}HelpBlock`} muted>{describe}</Form.Text> : ""}
                    </Form.Group>
                ))}
            </Row>

            <div className="d-grid gap-2">
                <Button variant="primary" size="lg">
                    Confirm
                </Button>
                <Button variant="secondary" size="lg" onClick={changeCanvas}>Cancel</Button>
            </div>
        </Form>
    );
}

function UserAccount() {
    return (
        <>
        </>
    );
}

export default OffCanvasUser;