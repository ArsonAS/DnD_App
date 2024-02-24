import {Button, Col, Container, Row} from "react-bootstrap";
import Form from "react-bootstrap/Form";

interface Props {}

export const Signup = () => {
    return (
        <Container fluid className="p-2 bg-dark">

            <Form className="p-2 m-5 bg-dark text-warning border border-warning rounded d-flex flex-column">

                <Form.Group controlId="userEmail" className="m-3 d-flex justify-content-between align-items-start">
                    <Form.Label>Adresse courriel</Form.Label>
                    <Form.Control type="email" placeholder="Saissir votre courriel"/>
                </Form.Group>

                <Form.Group controlId="userName" className="m-3 d-flex justify-content-between align-items-start">
                    <Form.Label>Nom d'usager</Form.Label>
                    <Form.Control type="text" placeholder="Saissir votre nom d'usager"/>
                </Form.Group>

                <Form.Group controlId="userPassword" className="m-3 d-flex justify-content-between align-items-start">
                    <Form.Label>Mot de passe</Form.Label>
                    <Form.Control type="password" placeholder="Saissir votre nom mot de passe"/>
                </Form.Group>

                <Form.Group controlId="passwordconfirmation" className="m-3 d-flex justify-content-between align-items-start">
                    <Form.Control type="password" placeholder="Confirmer votre nom mot de passe"/>
                </Form.Group>


                <Button variant="primary" type="submit" className="bg-warning text-dark border-warning">
                    Submit
                </Button>
            </Form>
        </Container>


    );
}
