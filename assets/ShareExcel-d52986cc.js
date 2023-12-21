import{_ as P,d as k,r as N,j as O,k as M,l as V,o as j,u,m as D,n as T,q as B,s as p,t as h,v as W,x as z,y as E,z as K,A as H,b as _,B as J,w as y,c as I,C as G,D as C,F as x,g as $,G as q,H as Y,I as Q,J as X,a as Z,f as R,h as A,i as ee,K as te,L as oe}from"./index-5111ef9a.js";import{E as re}from"./link-17ed8933.js";const ne=k({inheritAttrs:!1});function le(r,t,n,o,m,d){return N(r.$slots,"default")}var se=P(ne,[["render",le],["__file","/home/runner/work/element-plus/element-plus/packages/components/collection/src/collection.vue"]]);const ae=k({name:"ElCollectionItem",inheritAttrs:!1});function ie(r,t,n,o,m,d){return N(r.$slots,"default")}var ce=P(ae,[["render",ie],["__file","/home/runner/work/element-plus/element-plus/packages/components/collection/src/collection-item.vue"]]);const pe="data-el-collection-item",ue=r=>{const t=`El${r}Collection`,n=`${t}Item`,o=Symbol(t),m=Symbol(n),d={...se,name:t,setup(){const g=O(null),b=new Map;M(o,{itemMap:b,getItems:()=>{const f=u(g);if(!f)return[];const a=Array.from(f.querySelectorAll(`[${pe}]`));return[...b.values()].sort((l,s)=>a.indexOf(l.ref)-a.indexOf(s.ref))},collectionRef:g})}},v={...ce,name:n,setup(g,{attrs:b}){const c=O(null),f=V(o,void 0);M(m,{collectionItemRef:c}),j(()=>{const a=u(c);a&&f.itemMap.set(a,{ref:a,...b})}),D(()=>{const a=u(c);f.itemMap.delete(a)})}};return{COLLECTION_INJECTION_KEY:o,COLLECTION_ITEM_INJECTION_KEY:m,ElCollection:d,ElCollectionItem:v}},L=T({trigger:B.trigger,effect:{...p.effect,default:"light"},type:{type:h(String)},placement:{type:h(String),default:"bottom"},popperOptions:{type:h(Object),default:()=>({})},id:String,size:{type:String,default:""},splitButton:Boolean,hideOnClick:{type:Boolean,default:!0},loop:{type:Boolean,default:!0},showTimeout:{type:Number,default:150},hideTimeout:{type:Number,default:150},tabindex:{type:h([Number,String]),default:0},maxHeight:{type:h([Number,String]),default:""},popperClass:{type:String,default:""},disabled:{type:Boolean,default:!1},role:{type:String,default:"menu"},buttonProps:{type:h(Object)},teleported:p.teleported});T({command:{type:[Object,String,Number],default:()=>({})},disabled:Boolean,divided:Boolean,textValue:String,icon:{type:W}});T({onKeydown:{type:h(Function)}});ue("Dropdown");const de=T({trigger:B.trigger,placement:L.placement,disabled:B.disabled,visible:p.visible,transition:p.transition,popperOptions:L.popperOptions,tabindex:L.tabindex,content:p.content,popperStyle:p.popperStyle,popperClass:p.popperClass,enterable:{...p.enterable,default:!0},effect:{...p.effect,default:"light"},teleported:p.teleported,title:String,width:{type:[String,Number],default:150},offset:{type:Number,default:void 0},showAfter:{type:Number,default:0},hideAfter:{type:Number,default:200},autoClose:{type:Number,default:0},showArrow:{type:Boolean,default:!0},persistent:{type:Boolean,default:!0},"onUpdate:visible":{type:Function}}),fe={"update:visible":r=>z(r),"before-enter":()=>!0,"before-leave":()=>!0,"after-enter":()=>!0,"after-leave":()=>!0},me="onUpdate:visible",ve=k({name:"ElPopover"}),be=k({...ve,props:de,emits:fe,setup(r,{expose:t,emit:n}){const o=r,m=E(()=>o[me]),d=K("popover"),v=O(),g=E(()=>{var e;return(e=u(v))==null?void 0:e.popperRef}),b=E(()=>[{width:H(o.width)},o.popperStyle]),c=E(()=>[d.b(),o.popperClass,{[d.m("plain")]:!!o.content}]),f=E(()=>o.transition===`${d.namespace.value}-fade-in-linear`),a=()=>{var e;(e=v.value)==null||e.hide()},S=()=>{n("before-enter")},l=()=>{n("before-leave")},s=()=>{n("after-enter")},i=()=>{n("update:visible",!1),n("after-leave")};return t({popperRef:g,hide:a}),(e,w)=>(_(),J(u(Y),q({ref_key:"tooltipRef",ref:v},e.$attrs,{trigger:e.trigger,placement:e.placement,disabled:e.disabled,visible:e.visible,transition:e.transition,"popper-options":e.popperOptions,tabindex:e.tabindex,content:e.content,offset:e.offset,"show-after":e.showAfter,"hide-after":e.hideAfter,"auto-close":e.autoClose,"show-arrow":e.showArrow,"aria-label":e.title,effect:e.effect,enterable:e.enterable,"popper-class":u(c),"popper-style":u(b),teleported:e.teleported,persistent:e.persistent,"gpu-acceleration":u(f),"onUpdate:visible":u(m),onBeforeShow:S,onBeforeHide:l,onShow:s,onHide:i}),{content:y(()=>[e.title?(_(),I("div",{key:0,class:G(u(d).e("title")),role:"title"},C(e.title),3)):x("v-if",!0),N(e.$slots,"default",{},()=>[$(C(e.content),1)])]),default:y(()=>[e.$slots.reference?N(e.$slots,"reference",{key:0}):x("v-if",!0)]),_:3},16,["trigger","placement","disabled","visible","transition","popper-options","tabindex","content","offset","show-after","hide-after","auto-close","show-arrow","aria-label","effect","enterable","popper-class","popper-style","teleported","persistent","gpu-acceleration","onUpdate:visible"]))}});var ge=P(be,[["__file","/home/runner/work/element-plus/element-plus/packages/components/popover/src/popover.vue"]]);const F=(r,t)=>{const n=t.arg||t.value,o=n==null?void 0:n.popperRef;o&&(o.triggerRef=r)};var he={mounted(r,t){F(r,t)},updated(r,t){F(r,t)}};const ye="popover",we=Q(he,ye),Ee=X(ge,{directive:we});const _e={class:"main-div"},Ce={class:"main-controls"},ke={style:{"margin-left":"15px","font-size":"13px",color:"#a39e9e"}},Se=R("div",{id:"luckysheet",class:"main-lucky"},null,-1),U="defaultGridKey",Ne=k({__name:"ShareExcel",setup(r){const t={}.VITE_APP_BASE_API,n={}.VITE_APP_WEBSOCKET_URL,o=`${t}/excelWorkSheet/loadUrl`,m=`${t}/excelWorkSheet/exportExcel/${U}`,v=new Date().getTime()+"_aoe",g=`${n}?u=${v}&ly=1`,b=`${n}?g=${U}&u=${v}&ly=2`,c=O([]);async function f(){const l=new WebSocket(b);l.addEventListener("open",function(){console.info("[表格群组] WebSocket服务连接成功"),setInterval(()=>{l.send("1")},6e5)}),l.addEventListener("message",function(s){const i=JSON.parse(s.data);i.code===2502&&(c.value=i.data),console.log("[表格群组] 接收到消息 ",i)}),l.onclose=function(){console.log("[表格群组] WebSocket服务关闭.")},l.addEventListener("error",function(s){console.log("加入 [表格群组] 异常 : ",s)})}async function a(){const l={container:"luckysheet",gridKey:U,title:"默认表格",lang:"zh",loadUrl:o,allowUpdate:!0,updateUrl:g,allowEdit:!0,showtoolbarConfig:{undoRedo:!0,paintFormat:!0,currencyFormat:!0,percentageFormat:!0,numberDecrease:!0,numberIncrease:!0,moreFormats:!0,font:!0,fontSize:!0,bold:!0,italic:!0,strikethrough:!0,underline:!0,textColor:!0,fillColor:!0,border:!0,mergeCell:!0,horizontalAlignMode:!0,verticalAlignMode:!0,textWrapMode:!0,textRotateMode:!0,image:!0,link:!0,chart:!0,postil:!0,pivotTable:!0,function:!0,frozenMode:!0,sortAndFilter:!0,conditionalFormat:!0,dataVerification:!0,splitColumn:!0,screenshot:!0,findAndReplace:!0,protection:!0,print:!0},cellRightClickConfig:{copy:!0,copyAs:!0,paste:!0,insertRow:!0,insertColumn:!0,deleteRow:!0,deleteColumn:!0,deleteCell:!0,hideRow:!0,hideColumn:!0,rowHeight:!0,columnWidth:!0,clear:!0,matrix:!0,sort:!0,filter:!0,chart:!0,image:!0,link:!0,data:!0,cellFormat:!0},forceCalculation:!0,sheetFormulaBar:!0,showsheetbar:!0};luckysheet.create(l)}async function S(){await ee.get(m,{responseType:"blob"}).then(l=>{console.info("导出Excel请求：",l);let s=window.URL.createObjectURL(l.data),i=document.createElement("a");i.href=s,i.download=new Date().getTime()+".xlsx",i.click(),window.URL.revokeObjectURL(s),ElMessage({message:"导出成功！",type:"success"})}).catch(l=>{})}return j(async()=>{}),Z(async()=>{window.document.title="共享编辑",await f(),await a()}),(l,s)=>{const i=re,e=Ee;return _(),I("div",_e,[R("div",Ce,[A(i,{style:{"margin-right":"15px"},onClick:s[0]||(s[0]=w=>S())},{default:y(()=>[$("导出Excel")]),_:1}),A(e,{placement:"bottom",width:400,trigger:"click"},{reference:y(()=>[A(i,null,{default:y(()=>[$(" 查看当前表格协同用户("+C(c.value.length)+")",1)]),_:1})]),default:y(()=>[(_(!0),I(te,null,oe(c.value,w=>(_(),I("li",{key:w.uid},[$(C(w.uid)+" ",1),R("span",ke,C(w.time),1)]))),128))]),_:1})]),Se])}}});export{Ne as default};