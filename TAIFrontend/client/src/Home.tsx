import * as React from 'react';
import './App.css';
import { withAuth } from '@okta/okta-react';
import { Auth } from './App';
import MovieList from './MovieList';

const logo = require('./logo.jpg');

interface HomeProps {
  auth: Auth;
}

interface HomeState {
  authenticated: boolean;
}

export default withAuth(class Home extends React.Component<HomeProps, HomeState> {
  constructor(props: HomeProps) {
    super(props);
    this.state = {authenticated: false};
    this.checkAuthentication = this.checkAuthentication.bind(this);
    this.login = this.login.bind(this);
    this.logout = this.logout.bind(this);
  }

  async checkAuthentication() {
    const isAuthenticated = await this.props.auth.isAuthenticated();
    const {authenticated} = this.state;
    if (isAuthenticated !== authenticated) {
      this.setState({authenticated: isAuthenticated});
    }
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
    }

    return (
      <div className="App">
        <header className="App-header">
            <div className="logoclass">           <img src={logo} className="App-logo" alt="logo"/></div>
          <h1 className="textHeader">TV Shows Progresser</h1>
            <div className="divRight">{button}</div>
        </header>
          <div className="divLeft">{movieList}</div>
          {/*<div className="divRight">{button}</div>*/}
      </div>
    );
  }
});
