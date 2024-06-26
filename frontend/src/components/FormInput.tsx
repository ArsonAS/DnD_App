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
                    <Form.Label sm={3}>{label}</Form.Label>
                    <Form.Control type={type ?? "text"} value={value} onChange={onChange} placeholder={placeholder}/>
                    {errors.includes(formError)
                        ? (<p className="error text-warning">{formError}</p>)
                        : (<div className="error text-warning" />)}
                </Form.Group>
            </Col>
        </>
    );
};

export default FormInput;