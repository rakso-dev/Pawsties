import { Component } from "react";
import { Redirect, Link} from 'react-router-dom';
import axios from 'axios';
import './rescatista.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Container, Table, Alert, Button } from "reactstrap";
//import rescatistaLogin from './RescatistaLogin';

class RescListMascotas extends Component {
    constructor (props) {
        super(props);
        var rescatistaid = localStorage.getItem("rescatistaid");
        this.state = {
            rescatistaid: 1,// rescatistaid,
            items: [],
            isFetched: false
        }
    }

    componentDidMount() {
        const rescatistaid = this.state.rescatistaid;
        axios.get(`https://pawstiesapi.azurewebsites.net/pawstiesAPI/mascotas/rescatista/${rescatistaid}`, {
            headers: {
                'Content-type': 'application/json'
            }
            }).then(
                (response) => {
                    if (response.status === 200) 
                    this.setState({
                        items: response.data,
                        isFetched: true
                    })
                }, 
                (error) => {
                    if (error.response === 400 || error.response === 500) {
                        this.setState(
                            {
                                rescatistaid: 0
                            });
                    }
            }
        );
    }

    render() {
        if (this.state.rescatistaid == 0 || this.state.rescatistaid == null) {
            console.log(this.props.history.location.pathname)
            return (
                <Redirect 
                    to =
                    {
                        {
                            pathname: '/RescatistaLogin',
                            state: {
                                from: this.props.location.pathname
                            }
                        }
                    }
                />    
            )
        }

        if (!this.state.isFetched) {
            return (
                <Container>
                    <Alert color="primary">Loading...</Alert>
                </Container>
            )
        }

        const items = this.state.items;

        const ageFormatter = (age) => {
            if(age >= 365) {
                age = (age / 365>>0)
                return `${age} años`;
            }
            else 
            age = (age / 30>>0)
            return `${age} meses`;
        }

        return (
            <Container>
                <Table strip bordered hover>
                    <thead>
                        <tr>
                            <th>Nombre</th>
                            <th>Sexo</th>
                            <th>Edad</th>
                            <th>Color</th>
                            <th>Vacunado</th>
                            <th>Temperamento</th>
                            <th>Pelaje</th>
                            <th>Esterilizado</th>
                            <th>Discapacitado</th>
                            <th>Descripcion</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            items.map( item =>
                                <tr> 
                                    <td>{item.nombre}</td>
                                    <td>{item.sexo ? "macho" : "hembra"}</td>
                                    <td>{ageFormatter(item.edad)}</td>
                                    <td>{item.rColor}</td>
                                    <td>{item.vaxxed ? "Si": "No"}</td>
                                    <td>{item.rTemper}</td>
                                    <td>{item.pelaje ? "Largo" : "Corto"}</td>
                                    <td>{item.esterilizado ? "Si" : "No"}</td>
                                    <td>{item.discapacitado ? "Si" : "No"}</td>
                                    <td>{item.descripcion}</td>
                                    <td><Link to={`/editpet/${item.petid}`}>Editar</Link></td>
                                </tr> 
                            )
                        }
                    </tbody>

                </Table>
            </Container>
        )
    }
}

export default RescListMascotas;