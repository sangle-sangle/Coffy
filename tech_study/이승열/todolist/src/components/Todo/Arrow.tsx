import React, { Component } from 'react';
import '../../styles/Arrow.css'

interface Props {
    goPrev() : void;
    goNext() : void;
}

class Arrow extends Component<Props> {
    render() {
        return (
            <div className="arrowbox">
                <div className="arrow"
                    onClick={this.props.goNext}
                >
                    ->
                </div>
                <div className="arrow"
                    onClick={this.props.goPrev}
                >
                    {'<-'}
                </div>
            </div>
        );
    }
}

export default Arrow;