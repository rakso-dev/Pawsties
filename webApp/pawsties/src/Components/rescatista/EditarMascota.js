import {Component} from "react";
import {Redirect} from "react-router-dom";
import axios from "axios";

import {
    Container,
    FormGroup,
    Button,
    Label,
    Input,
    Form,
    Col,
    Alert
} from 'reactstrap';

class EditarMascota extends Component {
    constructor(props) {
        super(props)
        this.state = {
            petid: '',
            nombre: '',
            sexo: '',
            edad: '',
            rColor: '',
            vaxxed: '',
            rTemper: '',
            pelaje: '',
            esterilizado: '',
            discapacitado: '',
            descripcion: '',
            talla: 0,

            isSubmitted: false,
            error: false,
            isCancelled: false
        }
    }

    componentDidMount () {
        const id = this.props.match.params.id
        axios.get(`http://localhost:5000/pawstiesAPI/perro/${id}`, 
        {
            headers: {
                'Content-type': 'application/json'
            }
        }).then(
            (response) => {
                if(response.status === 200) {
                    const data = response.data;
                    console.log(data);
                    this.setState(
                        {
                            petid: data.petid,
                            nombre: data.nombre,
                            sexo: data.sexo,
                            edad: data.edad,
                            rColor: data.color,
                            vaxxed: data.vaxxed,
                            rTemper: data.rTemper,
                            pelaje: data.pelaje,
                            esterilizado: data.esterilizado,
                            discapacitado: data.discapacitado,
                            descripcion: data.descripcion,
                            talla: data.talla
                        }
                    )
                }
            },
            (error) => {
                if(error.status === 400) {
                    axios.get(`http://localhost:5000/pawstiesAPI/gato/${id}`,
                    {
                        headers: {
                            'Content-type': 'application/json'
                        }
                    }).then(
                        (response) => {
                            if (this.response === 200) {
                                const data = response.data;
                                console.log(data)
                                this.setState(
                                    {
                                        petid: data.petid,
                                        nombre: data.nombre,
                                        sexo: data.sexo,
                                        edad: data.edad,
                                        rColor: data.color,
                                        vaxxed: data.vaxxed,
                                        rTemper: data.rTemper,
                                        pelaje: data.pelaje,
                                        esterilizado: data.esterilizado,
                                        discapacitado: data.discapacitado,
                                        descripcion: data.descripcion
                                    }
                                )
                            }
                        }
                    );
                }
            }
        );
    }

    add () {
        if (this.state.talla == 0) {
            axios.put(`http://localhost:5000/pawstiesAPI/gato/${this.state.petid}`, {
                petid: this.state.petid,
                nombre: this.state.nombre,
                sexo: this.state.sexo,
                edad: this.state.edad,
                rColor: this.state.color,
                vaxxed: this.state.vaxxed,
                rTemper: this.state.rTemper,
                pelaje: this.state.pelaje,
                esterilizado: this.state.esterilizado,
                discapacitado: this.state.discapacitado
            },
            {
                headers: {
                    'Content-type': "application/json"
                }
            }).then(
                (response) => {
                    if (response === 200) {
                        this.setState (
                            {
                                isSubmitted: true,
                                error: false
                            }
                        )
                    }
                },
                (error) => {
                    this.setState (
                        {
                            isSubmitted: false,
                            error: true
                        }
                    )
                    console.log(error);
                }
            )
        }
        else {
            axios.put(`http://localhost:5000/pawstiesAPI/perro/${this.state.petid}`, {
                petid: this.state.petid,
                nombre: this.state.nombre,
                sexo: this.state.sexo,
                edad: this.state.edad,
                rColor: this.state.color,
                vaxxed: this.state.vaxxed,
                rTemper: this.state.rTemper,
                pelaje: this.state.pelaje,
                esterilizado: this.state.esterilizado,
                discapacitado: this.state.discapacitado,
                rtalla: this.talla
            },
                {
                    headers: {
                        'Content-type': "application/json"
                    }
                }).then(
                    (response) => {
                        if (response === 200) {
                            this.setState(
                                {
                                    isSubmitted: true,
                                    error: false
                                }
                            )
                        }
                    },
                    (error) => {
                        this.setState(
                            {
                                isSubmitted: false,
                                error: true
                            }
                        )
                        console.log(error);
                    }
                )
        }
    }

    cancel(e) {
        this.setState ({
            isCancelled: true
        });
    }

    handleChange(e) {
        this.setState (
            {
                [e.target.name]: e.target.value
            }
        );
    }

    render() {
        if(this.state.isCancelled){
            return (
                <Redirect
                    to=
                    {
                        {
                            pathname: '/RescListMascota',
                            state: {
                                from: this.props.location
                            }
                        }
                    }
                />    
            );
        }

        return (
            <Container classname="App">
                <h4 classname="PageHeading">Introduzca la informacion de la mascota</h4>
                <Alert 
                    isOpen={this.state.isSubmitted}
                    color={!this.state.error ? "success" : "warning"}
                    toggle={() => this.setState ({isSubmitted: false})}
                >
                    {!this.state.error ? "Se guardaron los datos" : "Ocurrio un error durante la modificacion"}
                </Alert>
                <Form className="form">
                    <Col>
                        <FormGroup row>
                            <Label for="name" sm={2}>Nombre</Label>
                            <Col sm={2}>
                                <Input type="text" name="nombre" onChange={this.handleChange} value={this.state.nombre} />
                            </Col>
                        </FormGroup>
                        <FormGroup row>
                            <Label for="name">Sexo</Label>
                            <Col sm={2}>
                            <Input type="checkbox" name="sexo" value={this.state.sexo} onChange={this.handleChange}>Selecciona para macho, deja en blanco para hembra</Input>
                            </Col>
                        </FormGroup>
                        <FormGroup row>
                            <Label for="name">Fecha de nacimiento</Label>
                            <Col sm={2}>
                                <Input bsSize="md" type="date" name="edad" value={this.state.edad} onChange={this.handleChange} />
                            </Col>
                        </FormGroup>
                        <FormGroup row>
                            <Label for="name">Color</Label>
                            <Col sm={2}>                                
                                <Input type="select" name="rColor" value={this.state.rColor} onChange={this.handleChange}>
                                    <option>1</option>
                                    <option>2</option>
                                    <option>3</option>
                                    <option>4</option>
                                    <option>5</option>
                                    <option>6</option>
                                </Input>
                            </Col>
                        </FormGroup>
                        <FormGroup>
                            <Label for="name">Vacunado</Label>
                            <Col sm={2}>
                                <Input type="checkbox" name="vaxxed" value={this.state.vaxxed} onChange={this.handleChange}/>
                            </Col>
                        </FormGroup>
                        <FormGroup>
                            <Label for="name">Temperamento</Label>
                            <Col sm={2}>
                                <Input name="rTemper" type="select" value={this.state.rTemper} onChange={this.handleChange}>
                                    <option>1</option>
                                    <option>2</option>
                                    <option>3</option>
                                    <option>4</option>
                                </Input>
                            </Col>
                        </FormGroup>
                        <FormGroup>
                            <Label for="name">Pelaje</Label>
                            <Col sm={2}>
                                <Input type="checkbox" name="pelaje" value={this.state.pelaje} onChange={this.handleChange}>Selecciona si es largo</Input>
                            </Col>
                        </FormGroup>
                        <FormGroup>
                            <Label for="name">Esterilizado</Label>
                            <Col sm={2}>
                                <Input type="checkbox" name="esterilizado" value={this.state.esterilizado} onChange={this.handleChange} />
                            </Col>
                        </FormGroup>
                        <FormGroup>
                            <Label for="name">Discapacitado</Label>
                            <Col sm={2}>
                                <Input type="checkbox" name="discapacitado" value={this.state.discapacitado} onChange={this.handleChange} />
                            </Col>
                        </FormGroup>
                        <FormGroup>
                            <Label for="name">Talla</Label>
                            <Col sm={2}>
                                <Input name="talla" type="select" value={this.state.talla} onChange={this.handleChange}>
                                    <option>1</option>
                                    <option>2</option>
                                    <option>3</option>
                                    <option>4</option>
                                </Input>
                            </Col>
                        </FormGroup>
                        <FormGroup>
                            <Label for="name">Descripcion</Label>
                            <Col sm={2}>
                                <Input type="text" name="descripcion" onChange={this.handleChange} value={this.state.descripcion} />
                            </Col>
                        </FormGroup>
                    </Col>  
                    <Col>
                        <FormGroup row>
                            <Col sm={5}>
                            </Col>
                            <Col sm={1}>
                                <Button color="primary" onClick={this.add}>Enviar</Button>
                            </Col>
                            <Col sm={1}>
                                <Button color="secondary" onClick={this.cancel} >Cancelar</Button>
                            </Col>
                            <Col sm={5}>
                            </Col>
                        </FormGroup>
                    </Col>  
                </Form>    
            </Container>
        );
    }
 }

export default EditarMascota;