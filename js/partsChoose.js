

function assembleTbody(data,partNo) {
    var tbody = '';
    if(partNo == 'fl'){
        return assembleFlywheelTBody(data);
    }
    if(partNo == 'flk'){
        return assembleFlywheelShellTBody(data);
    }
    if(partNo == 'dl'){
        return assembleIdlerTBody(data);
    }
    return tbody;
}

function delUndefined(val) {
    if(typeof (val) == "undefined"){
        return "";
    }else{
        return val;
    }
}

function associPart(data){
    //关闭tab页
    for (var key in data){
        if(parent.$("#"+key).length>0){
            parent.document.getElementById(key).value = data[key];
        }
    }
    parent.$.jBox.close(true);
}


/*
 * 惰轮
 */
function assembleIdlerTBody(data) {
    var tbody = '';
    for(var i=0;i<data.length;i++){
        tbody += '<tr><th>'+delUndefined(data[i].	dl0001	)+'</th>'
            +'<th>'+delUndefined(data[i].	dl0002	)+'</th>'
            +'<th>'+delUndefined(data[i].	dl0003	)+'</th>'
            +'<th>'+delUndefined(data[i].	dl0004	)+'</th>'
            +'<th>'+delUndefined(data[i].	dl0005	)+'</th>'
            +'<th>'+delUndefined(data[i].	dl0006	)+'</th>'
            +'<th>'+delUndefined(data[i].	dl0007	)+'</th>'
            +'<th>'+delUndefined(data[i].	dl0008	)+'</th>'
            +'<th>'+delUndefined(data[i].	dl0009	)+'</th>'
            +'<th>'+delUndefined(data[i].	dl0010	)+'</th>'
            +'<th>'+delUndefined(data[i].	dl0011	)+'</th>'
            +'<th>'+delUndefined(data[i].	dl0012	)+'</th>'
            +'<th>'+delUndefined(data[i].	dl0013	)+'</th></tr>'
    }
    return tbody;

}

/*
 * 飞轮
 */
 function assembleFlywheelTBody(data) {
     var tbody = '';
     for(var i=0;i<data.length;i++){
         tbody  += '<tr>'
             +'<td><a onclick=\'associPart('+JSON.stringify(data[i])+')\'>关联</a></td>'
             +'<td>'+delUndefined(data[i].fl0001)+'</td>'
             +'<td>'+delUndefined(data[i].fl0002)+'</td>'
             +'<td>'+delUndefined(data[i].fl0003)+'</td>'
             +'<td>'+delUndefined(data[i].fl0004)+'</td>'
             +'<td>'+delUndefined(data[i].fl0005)+'</td>'
             +'<td>'+delUndefined(data[i].fl0006)+'</td>'
             +'<td>'+delUndefined(data[i].fl0007)+'</td>'
             +'<td>'+delUndefined(data[i].fl0008)+'</td>'
             +'<td>'+delUndefined(data[i].fl0009)+'</td>'
             +'<td>'+delUndefined(data[i].fl0010)+'</td>'
             +'<td>'+delUndefined(data[i].fl0011)+'</td>'
             +'<td>'+delUndefined(data[i].fl0012)+'</td>'
             +'<td>'+delUndefined(data[i].fl0013)+'</td>'
             +'<td>'+delUndefined(data[i].fl0014)+'</td>'
             +'<td>'+delUndefined(data[i].fl0015)+'</td>'
             +'<td>'+delUndefined(data[i].fl0016)+'</td>'
             +'<td>'+delUndefined(data[i].fl0017)+'</td>'
             +'<td>'+delUndefined(data[i].fl0018)+'</td>'
             +'<td>'+delUndefined(data[i].fl0019)+'</td>'
             +'<td>'+delUndefined(data[i].fl0020)+'</td>'
             +'<td>'+delUndefined(data[i].fl0021)+'</td>'
             +'<td>'+delUndefined(data[i].fl0022)+'</td>'
             +'<td>'+delUndefined(data[i].fl0023)+'</td>'
             +'<td>'+delUndefined(data[i].fl0024)+'</td>'
             +'<td>'+delUndefined(data[i].fl0025)+'</td>'
             +'<td>'+delUndefined(data[i].fl0026)+'</td></tr>'
     }
     return tbody;
 }




