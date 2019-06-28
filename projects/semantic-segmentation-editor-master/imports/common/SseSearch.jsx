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

var infoStr=localStorage.getItem("imagesres");
var info=JSON.parse(infoStr);
console.log("info",info)
var tempPcd=[];
var tempJpg=[];
console.log(window.location.pathname);
var tempUrlNow=[];
for (var x of info) {
  if (x.editUrl==window.location.pathname)
  {
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
// console.log("temp",temp);

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

const formatGroupLabel = data => (
  <div style={groupStyles}>
    <span>{data.label}</span>
    <span style={groupBadgeStyles}>{data.options.length}</span>
  </div>
);

function logChange(val) {
  console.log("label",val.label);
  if(val.label.slice(-4)==".jpg")
  {
    window.open("http://localhost:3000/edit/%2Fpcd%2F456%2F1022(copy).pcd", null, "Height:100px; Width:100px; resizable:yes");
  }
  else
  {
    for (var i=0;i<info.length;i++)
    {
      if(val.label==(info[i].name))
      {
        window.location.pathname=(info[i].editUrl);
      }
    }
  }
}

export default () => (
  <Select
    // defaultValue={urlNow}
    // defaultValue={window.location.pathname}
    options={groupedOptions}
    formatGroupLabel={formatGroupLabel}
    onChange={logChange}
  />
);
