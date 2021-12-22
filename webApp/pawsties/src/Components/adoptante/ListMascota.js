import { Component } from "react";
import { Redirect, Link } from "react-router-dom";
import axios from "axios";

import './adoptante.css';
import { Container, Table, Alert } from "reactstrap";

class ListMascotas extends Component {
    constructor (props) {
        super(props);
        this.state = {
            items: [],
            distance: 3000,
            longitude: 0,
            latitude: 0
        }
    }

    componentDidMount () {
        navigator.geolocation.getCurrentPosition(success => {
            const lat = success.coords.latitude;
            const long = success.coords.longitude;
            axios.get(`https://pawstiesapi.azurewebsites.net/pawstiesAPI/mascotas/get/${this.state.distance}`, {
                latitude: lat,
                longitude: long
            }, {
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(
                    (response) => {
                        if (this.response === 200) {
                            this.setState({
                                items: response.data
                            });
                        }
                    },
                    (error) => {
                        console.log(error);
                    }
                );
        },
        error => {
            console.log(error);
        });
    }

    render() {

        /*if (!this.state.isFetched) {
            return (
                <Container>
                    <Alert color="primary">Loading...</Alert>
                </Container>
            )
        }*/

        const items = this.state.items;

        const ageFormatter = (age) => {
            if (age >= 365) {
                age = (age / 365 >> 0)
                return `${age} años`;
            }
            else
                age = (age / 30 >> 0)
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
                        </tr>
                    </thead>
                    <tbody>
                        {
                            items.map(item =>
                                <tr>
                                    <td>{item.nombre}</td>
                                    <td>{item.sexo ? "macho" : "hembra"}</td>
                                    <td>{ageFormatter(item.edad)}</td>
                                    <td>{item.rColor}</td>
                                    <td>{item.vaxxed ? "Si" : "No"}</td>
                                    <td>{item.rTemper}</td>
                                    <td>{item.pelaje ? "Largo" : "Corto"}</td>
                                    <td>{item.esterilizado ? "Si" : "No"}</td>
                                    <td>{item.discapacitado ? "Si" : "No"}</td>
                                    <td>{item.descripcion}</td>
                                </tr>
                            )
                        }
                    </tbody>

                </Table>
            </Container>
        )
    }
}

export default ListMascotas;