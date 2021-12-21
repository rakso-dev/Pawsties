import { Component } from "react";
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';
import {
    Form,
    Alert,
    FormGroup,
    Label,
    Input,
    Button  } from 'reactstrap';

class RescatistaLogin extends Component {
    
    constructor (props) {
        super(props);
        const prev = this.props.location.state.from
        console.log(prev);
        this.state = {
            Mail: '',
            Password: '',
            error: false,
            Prev: prev
        }
        this.doLogin = this.doLogin.bind(this);
    }

    doLogin() {
        axios.put('http://localhots:5000/pawstiesAPI/rescatista',
        {
            "mail": this.state.Mail,
            "password": this.state.Password
        }, {
            headers: {
                'Content-type': 'application/json'
            }
        }).then(
            (response) => {
                if (response.status === 200){
                    const json = response.data;
                    localStorage.setItem("rescatistaid", json.rescatistaid);
                    console.log(this.state.Prev);
                    this.props.history.push(this.state.Prev);
                }
            },
            (error) => {
                if (error.status === 400 || error.status === 500) {
                    this.setState (
                        {
                            error: true
                        }
                    );
                }
                console.log("Exception " + error);
            }
        );
    }

    handleChange = (e) => {
        this.setState(
            {
                [e.target.name]: e.target.value
            }
        );
    }

    render () {
        return (
            <div>
                <Alert 
                    isOpen={this.state.error}
                    color="danger"
                    toggle={() => this.setState ({error: false})}
                >
                  User or password incorrect!  
                </Alert>    
                <div className="Login">
                    <Form>
                        <FormGroup>
                            <Label>Correo</Label>
                            <Input name="Mail" type="text" onChange={this.handleChange} value={this.state.Mail} />
                        </FormGroup>
                        <FormGroup>
                            <Label>Contraseña</Label>
                            <Input type="password" name="Password" onChange={this.handleChange} value={this.state.Password} />
                        </FormGroup>
                        <Button>
                            <Button block type="button" onClick={ this.doLogin() }>
                                login
                            </Button>
                        </Button>
                    </Form>
                </div>
            </div>
        );
    }
}

export default RescatistaLogin;