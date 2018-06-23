import * as React from 'react';
import './App.css';
import {withAuth} from '@okta/okta-react';
import {Auth} from './App';

const logo = require('./logo.jpg');
import MovieList from './MovieList';
import axios from 'axios';

interface HomeProps {
    auth: Auth;
    text: String;
}

interface MovieFound {
    id: string;
    title: string;
}

interface HomeState {
    authenticated: boolean;
    list: Array<MovieFound>;
    inputval: ' ';
}

export default withAuth(class Home extends React.Component<HomeProps, HomeState> {
    constructor(props: HomeProps) {
        super(props);
        this.state = {authenticated: false, list: [], inputval: ' '};
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

    async handleSubmit(event) {
        alert('A name was submitted: ' + this.state.inputval);
        event.preventDefault();
        axios.get('https://api.themoviedb.org/3/search/movie', {
            params: {
                api_key: '96e922a6bdf38660f798b285e289fd8a',
                query: this.state.inputval
            }
        })
            .then(response => {
                this.setState({
                    list: response.data.results
                });
                console.log(this.state.list);

            });
    }
    handleChange(event) {
        // Because we named the inputs to match their corresponding values in state, it's
        // super easy to update the state    [e.target.name]
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
        let button = null;
        let movieList = null;
        let noMovieList = null;
        let listFound = this.state.list.map((d) => <li key={d.id}>{d.title}</li>);
        if (authenticated) {
            button = (
                <div className="Buttons">
                    <button onClick={this.logout}>Logout</button>
                </div>
            );
            movieList = (
                <div className="Movies">
                    <MovieList auth={this.props.auth}/>
                </div>
            );

        } else {
            button = (
                <div className="Buttons">
                    <button onClick={this.login}>Login</button>
                </div>
            );
            noMovieList = (
                <div className="noMovieList">
                    Log in to see example movies.
                </div>
            );
        }

        return (
            <div className="App">
                <header className="App-header">
                    <div className="logoclass"><img src={logo} className="App-logo" alt="logo"/></div>
                    <h1 className="textHeader">TV Shows Progresser</h1>
                    <div className="divRight">{button}</div>
                </header>
                <div className="divLeft">{movieList}{noMovieList}</div>
                <form onSubmit={this.handleSubmit}>
                    <label>
                        <input type="text" value={this.state.inputval} onChange={this.handleChange}/>
                    </label>
                    <div>
                        <input type="submit" value="Search"/>
                    </div>
                </form>
                <div className="divSearch">{listFound}
                </div>
            </div>
        );
    }
});