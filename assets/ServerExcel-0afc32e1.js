import{d as y,o as k,a as w,c as E,f as c,h as l,w as a,i as u,E as s,b as _,g as i}from"./index-5111ef9a.js";import{E as C}from"./link-17ed8933.js";const v={class:"main-div"},b={class:"main-controls"},R=c("div",{id:"luckysheet",class:"main-lucky"},null,-1),n="defaultGridKey",M=y({__name:"ServerExcel",setup(A){const o={}.VITE_APP_BASE_API,d=`${o}/excelWorkSheet/loadUrl`,m=`${o}/excelWorkSheet/exportExcel/${n}`,p=`${o}/excelWorkSheet/save`;async function h(){const t={container:"luckysheet",gridKey:n,title:"默认表格",lang:"zh",loadUrl:d,allowEdit:!0,showtoolbarConfig:{undoRedo:!0,paintFormat:!0,currencyFormat:!0,percentageFormat:!0,numberDecrease:!0,numberIncrease:!0,moreFormats:!0,font:!0,fontSize:!0,bold:!0,italic:!0,strikethrough:!0,underline:!0,textColor:!0,fillColor:!0,border:!0,mergeCell:!0,horizontalAlignMode:!0,verticalAlignMode:!0,textWrapMode:!0,textRotateMode:!0,image:!0,link:!0,chart:!0,postil:!0,pivotTable:!0,function:!0,frozenMode:!0,sortAndFilter:!0,conditionalFormat:!0,dataVerification:!0,splitColumn:!0,screenshot:!0,findAndReplace:!0,protection:!0,print:!0},cellRightClickConfig:{copy:!0,copyAs:!0,paste:!0,insertRow:!0,insertColumn:!0,deleteRow:!0,deleteColumn:!0,deleteCell:!0,hideRow:!0,hideColumn:!0,rowHeight:!0,columnWidth:!0,clear:!0,matrix:!0,sort:!0,filter:!0,chart:!0,image:!0,link:!0,data:!0,cellFormat:!0},forceCalculation:!0,sheetFormulaBar:!0,showsheetbar:!0};luckysheet.create(t)}async function x(){const t=JSON.stringify(luckysheet.toJson()),e={gridKey:n,data:t};u.post(p,e).then(()=>{s({message:"保存成功！",type:"success"})})}async function f(){await u.get(m,{responseType:"blob"}).then(t=>{console.info("导出Excel请求：",t);let e=window.URL.createObjectURL(t.data),r=document.createElement("a");r.href=e,r.download=new Date().getTime()+".xlsx",r.click(),window.URL.revokeObjectURL(e),s({message:"导出成功！",type:"success"})}).catch(t=>{})}return k(()=>{}),w(async()=>{window.document.title="服务编辑",await h()}),(t,e)=>{const r=C;return _(),E("div",v,[c("div",b,[l(r,{style:{"margin-right":"20px"},onClick:e[0]||(e[0]=g=>x())},{default:a(()=>[i("保存Excel")]),_:1}),l(r,{style:{"margin-right":"5px"},onClick:e[1]||(e[1]=g=>f())},{default:a(()=>[i("导出Excel")]),_:1})]),R])}}});export{M as default};
