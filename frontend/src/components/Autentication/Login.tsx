import Form from "react-bootstrap/Form";
import {Button} from "react-bootstrap";

interface Props {}

export const Login = () =>{
    return (
        <Form>
            <Form.Group controlId="userName">
                <Form.Label>Nom d'usager</Form.Label>
                <Form.Control type="text" placeholder="Saissir votre nom d'usager"/>
            </Form.Group>

            <Form.Group controlId="userPassword">
                <Form.Label>Mot de passe</Form.Label>
                <Form.Control type="password" placeholder="Saissir votre nom mot de passe"/>
            </Form.Group>

            <Button variant="primary" type="submit">
                Submit
            </Button>
        </Form>
    );
}