import * as React from 'react';
import './App.css';
import { withAuth } from '@okta/okta-react';
import { Auth } from './App';
const logo = require('./logo.jpg');

interface HomeProps {
    auth: Auth;
    text: String;
}

interface HomeState {
    authenticated: boolean;
    inputval: ' ';
}

export default withAuth(class Home extends React.Component<HomeProps, HomeState> {
    constructor(props: HomeProps) {
        super(props);
        this.state = {authenticated: false, inputval: ' '};
        this.checkAuthentication = this.checkAuthentication.bind(this);
        this.login = this.login.bind(this);
        this.logout = this.logout.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleChange = this.handleChange.bind(this);
    }

    async checkAuthentication() {
        const isAuthenticated = await this.props.auth.isAuthenticated();
        const {authenticated} = this.state;
        if (isAuthenticated !== authenticated) {
            this.setState({authenticated: isAuthenticated});
        }
    }

    handleSubmit(event) {
        alert('A name was submitted: ' + this.state.inputval);
        fetch('127.0.0.1:8080/search', {
          method: 'POST',
          body: event.target.value
        });
        event.preventDefault();
    }

    handleChange(event) {
        this.setState({inputval: event.target.value});
    }

    async componentDidMount() {
        await this.checkAuthentication();
    }

    async componentDidUpdate() {
        await this.checkAuthentication();
    }

    async login() {
        this.props.auth.login('/');
    }

    async logout() {
        this.props.auth.logout();
    }
    render() {
        const {authenticated} = this.state;
        let button ;
        if (authenticated) {
            button = (
                <div className="Buttons">
                    <button onClick={this.logout}>Logout</button>
                </div>
            );
        } else {
            button = (
                <div className="Buttons">
                    <button onClick={this.login}>Login</button>
                </div>
            );
        }
        return (
            <div className="App">
                <header className="App-header">
                    <div className="logoclass">           <img src={logo} className="App-logo" alt="logo"/></div>
                    <h1 className="textHeader">TV Shows Progresser</h1>
                    <div className="divRight">{button}</div>
                </header>
                <form onSubmit={this.handleSubmit}>
                    <label>
                        Name:
                        <input type="text" value={this.state.inputval} onChange={this.handleChange} />
                    </label>
                    <input type="submit" value="Search" />
                </form>
            </div>
        );
    }
});
