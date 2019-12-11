import React from 'react';
import Select from 'react-select';

const groupStyles = {
  display: 'flex',
  alignItems: 'center',
  justifyContent: 'space-between',
};

const groupBadgeStyles = {
  backgroundColor: '#EBECF0',
  borderRadius: '2em',
  color: '#172B4D',
  display: 'inline-block',
  fontSize: 12,
  fontWeight: 'normal',
  lineHeight: '1',
  minWidth: 1,
  padding: '0.16666666666667em 0.5em',
  textAlign: 'center',
};


export default class SseSearchNew extends React.Component {
    constructor(url) {
        super();
        this.url=url;
        console.log("url",this.url);
    }

    

// if (this.info==null)
//   {
//     return ;
//   }
myinit(){
    var infoStr=localStorage.getItem("imagesres");
    var info=JSON.parse(infoStr);
    var tempPcd=[];
    var tempJpg=[];
    var tempUrlNow=[];
    // var viewUrl;
    for (var x of info) {
      if (x.editUrl==window.location.pathname)
      {
        console.log("x.editUrl",x.editUrl,"window.location.pathname",window.location.pathname);
        tempUrlNow.push({"label":x.name})
      }
      if (x.name.slice(-4)==".pcd")
      {
        tempPcd.push({"label":x.name});
      }
      if (x.name.slice(-4)==".jpg")
      {
        tempJpg.push({"label":x.name});
      }
    }
    
    var groupedOptions=[
      {
        label: 'NOW',
        options: tempUrlNow,
      },
      {
        label: 'PCD',
        options: tempPcd,
      },
      {
        label: 'JPG',
        options: tempJpg,
      },
    ]

    return groupedOptions;
}


formatGroupLabel = data => (
  <div style={groupStyles}>
    <span>{data.label}</span>
    <span style={groupBadgeStyles}>{data.options.length}</span>
  </div>
);

logChange(val) {
    var infoStr=localStorage.getItem("imagesres");
    var info=JSON.parse(infoStr);
    for (var i=0;i<info.length;i++)
    {
      if(val.label==(info[i].name))
      {
        window.location.pathname=(info[i].editUrl);
      }
    }
}

    render() {
        var array=this.myinit();
        return <Select
        // defaultValue={urlNow}
        // defaultValue={window.location.pathname}
        options={array}
        formatGroupLabel={this.formatGroupLabel}
        onChange={this.logChange}
        />
    }
}
  

