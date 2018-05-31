import * as React from 'react';
import { Auth } from './App';
// import GiphyImage from './GiphyImage';

interface Movie {
    id: string;
    title: string;
    posterPath: string;
}

interface MovieListProps {
    auth: Auth;
}

interface MovieListState {
    movies: Array<Movie>;
    isLoading: boolean;
    error: string;
}

class MovieList extends React.Component<MovieListProps, MovieListState> {

    constructor(props: MovieListProps) {
        super(props);

        this.state = {
            movies: [],
            isLoading: false,
            error: ''
        };
    }

    async componentDidMount() {
        this.setState({isLoading: true});

        try {
            const response = await fetch('http://localhost:8080/good-movies', {
                headers: {
                    Authorization: 'Bearer ' + await this.props.auth.getAccessToken()
                }
            });
            const data = await response.json();
            this.setState({movies: data, isLoading: false});
        } catch (err) {
            this.setState({error: err});
        }
    }

    render() {
        const {movies, isLoading, error} = this.state;

        if (isLoading) {
            return <p>Loading ...</p>;
        }

        if (error.length > 0) {
            return <p>Error: {error}</p>;
        }

        return (
            <div>
                {/*<h2>Movie List !</h2>*/}
                {movies.map((movie: Movie) =>
                    <div key={movie.id}>
                        <div className="item">
                        {/*<GiphyImage name={movie.posterPath}/>*/}
                        <div className="padder"><img src={movie.posterPath} /> </div>
                            {movie.title}<br/>
                        </div>
                    </div>
                )}
            </div>
        );
    }
}

export default MovieList;