import { Col, Form } from "react-bootstrap";


interface Props {
    label: string;
    value: string;
    onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
    errors: string[];
    formError: string;
    controlId: string;
    type?: string;
    placeholder?: string;
}

const FormInput = ({label, value, onChange, errors, formError, controlId, type, placeholder,}: Props) => {

    return (
        <>
            <Col sm={12} md={true}>
                <Form.Group controlId={controlId}>
                    <Form.Label>{label}</Form.Label>
                    <Form.Control type={type ?? "text"} value={value} onChange={onChange} placeholder={placeholder}/>
                    {errors.includes(formError) ?
                        (<p className="error fade-in">{formError}</p>) :
                        (<div className="error" />)}
                </Form.Group>
            </Col>
        </>
    );
};

export default FormInput;