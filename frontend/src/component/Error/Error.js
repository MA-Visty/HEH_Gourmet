import React from "react";
import Alert from 'react-bootstrap/Alert';
import { Link } from "react-router-dom";

function Error({type}) {
	switch (type) {
		case "notFound":
			return notFoundError();
		default:
			return defaultError();
	}
}

function notFoundError() {
	return (
		<Alert variant="danger">
			<Alert.Heading>Oh snap! You got an error!</Alert.Heading>
			<hr />
			<p className="mb-0">
				The page you try to access doesn't exist
			</p>
			<Link to="/">Go to home page</Link>
		</Alert>
	);
}

function defaultError() {
	return (
		<Alert variant="danger">
			<Alert.Heading>Oh snap! You got an error!</Alert.Heading>
			<hr />
			<p className="mb-0">
				Network Error
			</p>
		</Alert>
	);
}

export default Error;