import React from 'react';
import {CardText, CardTitle} from '@material-ui/core';
import {withStyles} from '@material-ui/core/styles';
import {Approval} from "mdi-material-ui";

class SseCurrentFolder extends React.Component {
    constructor() {
        super();
    }

    render() {

        const image = this.props.image;
        let name = image.name;
        if (!name) {
            const durl = decodeURIComponent(image.url);
            name = durl.substring(1 + durl.lastIndexOf("/"));
        }
        // const {classes} = this.props;
        return (
            <div className="sse-class-chooser vflex scroller" >
                <li>{name}</li>
            </div>
        );
    }
}

const styles = {
    card: {
        width: "345px",
    },
    media: {
        height: 0,
        paddingTop: '56.25%', // 16:9
    },
};

export default withStyles(styles)(SseCurrentFolder)