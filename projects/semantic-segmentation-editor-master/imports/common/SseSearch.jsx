import React from 'react';
import Select from 'react-select';
import { stateOptions } from '../common/data';

const groupStyles = {
  display: 'flex',
  alignItems: 'center',
  justifyContent: 'space-between',
};
const groupBadgeStyles = {
  backgroundColor: 'red',
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
var temp=[];
for (var x of info) {
  temp.push({"label":x.name});
  // temp.push(x.name);
}
console.log("temp",temp);

var groupedOptions=[
  {
    label: 'PCD',
    options: temp,
  }
]



const formatGroupLabel = data => (
  <div style={groupStyles}>
    <span>{data.label}</span>
    <span style={groupBadgeStyles}>{data.options.length}</span>
  </div>
);

function logChange(val) {
  console.log("Selected: " + val);
  console.log("label",val.label);
  for (var i=0;i<info.length;i++)
  {
    console.log("url",info[i].name)
    if(val.label==(info[i].name))
    {
      window.location.pathname=(info[i].editUrl);
    }
}
}

export default () => (
  <Select
    defaultValue={temp[1]}
    options={groupedOptions}
    formatGroupLabel={formatGroupLabel}
    onChange={logChange}
  />
);
