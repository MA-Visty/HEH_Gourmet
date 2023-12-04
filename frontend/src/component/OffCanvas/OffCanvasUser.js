import React, {useState} from 'react';
import { Offcanvas, Button, Row, Col, Form, ImputGroup } from 'react-bootstrap';

function OffCanvasUser({ show, onHide, type, ...props }) {
    return (
        <Offcanvas show={show} onHide={onHide} placement={'end'} {...props}>
            {type === "login" ? UserAccount()
                : type === "register" ? UserRegister()
                : UserLogin()
            }
        </Offcanvas>
    )
}

function UserLogin() {
    const [validated, setValidated] = useState(false);

    return (
        <>
            <Offcanvas.Header closeButton>
                <Offcanvas.Title>Login</Offcanvas.Title>
            </Offcanvas.Header>
            <Offcanvas.Body>
                <Form noValidate validated={validated}>
                    <Row className="mb-3">
                        <Form.Group>
                            <Form.Label>Login</Form.Label>
                            <Form.Control
                                required
                                type="text"
                                placeholder="Entrer your login"
                                defaultValue=""
                            />
                            <Form.Control.Feedback>Looks good!</Form.Control.Feedback>
                        </Form.Group>
                        <Form.Group>
                            <Form.Label>Password</Form.Label>
                            <Form.Control
                                required
                                type="password"
                                placeholder="Entrer your password"
                                aria-describedby="passwordHelpBlock"
                            />
                            <Form.Text id="passwordHelpBlock" muted>
                                Your password must be 8-20 characters long, contain letters and numbers,
                                and must not contain spaces, special characters, or emoji.
                            </Form.Text>
                        </Form.Group>
                    </Row>

                    <div className="d-grid gap-2">
                        <Button variant="primary" size="lg">
                            Login
                        </Button>
                        <Button variant="secondary" size="lg">
                            Register
                        </Button>
                    </div>
                </Form>
            </Offcanvas.Body>
        </>
    );
}

function UserRegister() {
    const [validated, setValidated] = useState(false);
    const labelFormGroup = [
        ["text", "Lastname", ""],
        ["text", "Firstname", ""],
        ["email", "Email", ""],
        ["text", "Login", ""],
        ["password", "Password", ""],
        ["password", "Confirm password", "Your password must be 8-20 characters long, contain letters and numbers,\n" +
        "                                    and must not contain spaces, special characters, or emoji."]
    ]

    return (
        <>
            <Offcanvas.Header closeButton>
                <Offcanvas.Title>Register</Offcanvas.Title>
            </Offcanvas.Header>
            <Offcanvas.Body>
                <Form noValidate validated={validated}>
                    <Row className="mb-3">
                        {labelFormGroup.map(([type, label, describe]) => (
                            <Form.Group key={label}>
                                <Form.Label>{label}</Form.Label>
                                <Form.Control
                                    required
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
                        <Button variant="secondary" size="lg">
                            Cancel
                        </Button>
                    </div>
                </Form>
            </Offcanvas.Body>
        </>
    );
}

function UserAccount() {
    return (
        <>
            <Offcanvas.Header closeButton>
                <Offcanvas.Title>Account</Offcanvas.Title>
            </Offcanvas.Header>
            <Offcanvas.Body>
            </Offcanvas.Body>
        </>
    );
}

export default OffCanvasUser;