(function(){try{var h=true,j=null,k=false;window.gbar.tev&&window.gbar.tev(3,"m");var aa=this,ea=function(a){var b=typeof a;if(b=="object")if(a){if(a instanceof Array)return"array";else if(a instanceof Object)return b;var c=Object.prototype.toString.call(a);if(c=="[object Window]")return"object";if(c=="[object Array]"||typeof a.length=="number"&&typeof a.splice!="undefined"&&typeof a.propertyIsEnumerable!="undefined"&&!a.propertyIsEnumerable("splice"))return"array";if(c=="[object Function]"||typeof a.call!="undefined"&&typeof a.propertyIsEnumerable!="undefined"&&!a.propertyIsEnumerable("call"))return"function"}else return"null";
else if(b=="function"&&typeof a.call=="undefined")return"object";return b},fa=function(a){return a.call.apply(a.bind,arguments)},ga=function(a,b){if(!a)throw Error();if(arguments.length>2){var c=Array.prototype.slice.call(arguments,2);return function(){var d=Array.prototype.slice.call(arguments);Array.prototype.unshift.apply(d,c);return a.apply(b,d)}}else return function(){return a.apply(b,arguments)}},l=function(){l=Function.prototype.bind&&Function.prototype.bind.toString().indexOf("native code")!=
-1?fa:ga;return l.apply(j,arguments)},m=function(a){var b=Array.prototype.slice.call(arguments,1);return function(){var c=Array.prototype.slice.call(arguments);c.unshift.apply(c,b);return a.apply(this,c)}},o=function(a,b){var c=a.split("."),d=aa;!(c[0]in d)&&d.execScript&&d.execScript("var "+c[0]);for(var e;c.length&&(e=c.shift());)if(!c.length&&b!==undefined)d[e]=b;else d=d[e]?d[e]:d[e]={}};var ha=function(){};(function(a){a.M=function(){return a.N||(a.N=new a)}})(ha);var q=j;var r={Z:1,ba:2,la:3,V:4,F:5,C:6,$:7,D:8,pa:9,ka:10,da:11,ja:12,ia:13,ea:14,ha:15,ga:16,na:17,X:18,fa:19,oa:20,ma:21,W:22,aa:23,ra:24,sa:25,qa:26,Y:27,ca:500};var s=window.gbar;var v={u:1,U:2,T:3,z:4,w:5,B:6,A:7,v:8};var x=[],B=j,C=function(a,b){var c=j;if(b)c={m:b};s.tev&&s.tev(a,"m",c)};var ia=new Function("a","return a");var E=function(a,b,c){var d={};d._sn=["m",b,c].join(".");s.logger.ml(a,d)};var F,pa=function(){F=/MSIE (\d+)\.(\d+);/.exec(navigator.userAgent);ja();o("gbar.addHover",ka);o("gbar.close",la);o("gbar.cls",ma);o("gbar.tg",na);s.adh("gbd4",function(){oa(r.F,!G)});s.adh("gbd5",function(){oa(r.C,!G)})},I="",G=undefined,J=undefined,K=undefined,L=undefined,qa=k,M=undefined,ra=["gbzt","gbgt","gbg0l","gbmt","gbml1"],N=function(a,b,c,d){var e="on"+b;if(a.addEventListener)a.addEventListener(b,c,!!d);else if(a.attachEvent)a.attachEvent(e,c);else{var f=a[e];a[e]=function(){var g=f.apply(this,
arguments),i=c.apply(this,arguments);return g==undefined?i:i==undefined?g:i&&g}}},O=function(a){return document.getElementById(a)},P=function(a){var b={};if(a.style.display!="none"){b.width=a.offsetWidth;b.height=a.offsetHeight;return b}var c=a.style,d=c.display,e=c.visibility,f=c.position;c.visibility="hidden";c.position="absolute";c.display="inline";var g;g=a.offsetWidth;a=a.offsetHeight;c.display=d;c.position=f;c.visibility=e;b.width=g;b.height=a;return b},sa=function(a){if(K===undefined){var b=
document.body.style;K=!(b.WebkitBoxShadow!==undefined||b.MozBoxShadow!==undefined||b.boxShadow!==undefined||b.BoxShadow!==undefined)}if(K){b=a.id+"-gbxms";var c=O(b);if(!c){c=document.createElement("span");c.id=b;c.className="gbxms";a.appendChild(c)}if(L===undefined)L=c.offsetHeight<a.offsetHeight/2;if(L){c.style.height=a.offsetHeight-5+"px";c.style.width=a.offsetWidth-3+"px"}}},ta=function(a,b){if(a){var c=a.style,d=b||O(I);if(d){a.parentNode&&a.parentNode.appendChild(d);d=d.style;d.width=a.offsetWidth+
"px";d.height=a.offsetHeight+"px";d.top="32px";d.left=c.left;d.right=c.right}}},S=function(){try{if(G){var a=O(I);if(a)a.style.visibility="hidden";var b=O(G);if(b){b.style.visibility="hidden";var c=b.getAttribute("aria-owner"),d=c?O(c):j;if(d){ua(d.parentNode,"gbto");d.blur()}}if(J){J();J=undefined}var e=s.ch[G];if(e){a=0;for(var f;f=e[a];a++)try{f()}catch(g){E(g,"sb","cdd1")}}G=undefined}}catch(i){E(i,"sb","cdd2")}},oa=function(a,b){var c={s:b?"o":"c"};a!=-1&&s.logger.il(a,c)},U=function(a,b){var c=
a.className;T(a,b)||(a.className+=(c!=""?" ":"")+b)},ua=function(a,b){var c=a.className,d=RegExp("\\s?\\b"+b+"\\b");if(c&&c.match(d))a.className=c.replace(d,"")},T=function(a,b){var c=a.className;return!!(c&&c.match(RegExp("\\b"+b+"\\b")))},na=function(a,b,c){try{a=a||window.event;c=c||k;if(!I){var d=document.createElement("iframe");d.frameBorder="0";I=d.id="gbs";d.src="javascript:''";O("gbw").appendChild(d)}if(!qa){N(document,"click",la);N(document,"keyup",va);qa=h}if(!c){a.preventDefault&&a.preventDefault();
a.returnValue=k;a.cancelBubble=h}if(!b){b=a.target||a.srcElement;for(var e=b.parentNode.id;!T(b.parentNode,"gbt");){if(e=="gb")return;b=b.parentNode;e=b.parentNode.id}}var f=b.getAttribute("aria-owns");if(f.length){b.focus();if(G==f)ma(f);else{var g=b.offsetWidth;a=0;do a+=b.offsetLeft||0;while(b=b.offsetParent);if(M===undefined){var i=document.body,t,D=document.defaultView;if(D&&D.getComputedStyle){var u=D.getComputedStyle(i,"");if(u)t=u.direction}else t=i.currentStyle?i.currentStyle.direction:i.style.direction;
M=t=="rtl"}b=M?k:h;i=M?k:h;if(f=="gbd")i=!i;var p=O("gb");p&&M&&U(p,"gbrtl");G&&S();var n=s.bh[f];if(n)for(var y=0,z;z=n[y];y++)try{z()}catch(ba){E(ba,"sb","t1")}p=a;n=i;var w=O(f);if(w){var A=w.style,Q=w.offsetWidth;if(Q<g){A.width=g+"px";Q=g;var wa=w.offsetWidth;if(wa!=g)A.width=g-(wa-g)+"px"}var R,H,ca=document.documentElement&&document.documentElement.clientWidth?document.documentElement.clientWidth:document.body.clientWidth;if(n){R=b?Math.max(ca-p-Q,5):ca-p-g;H=-(ca-p-g-R);if(F&&F.length>1){var xa=
new Number(F[1]);if(xa==6||xa==7&&document.compatMode=="BackCompat")H-=2}}else{R=b?p:Math.max(p+g-Q,5);H=R-p}var ya=O("gbw"),za=O("gb");if(ya&&za){var Aa=ya.offsetLeft;if(Aa!=za.offsetLeft)H-=Aa}sa(w);A.top="32px";A.right=n?H+"px":"auto";A.left=n?"auto":H+"px";A.visibility="visible";var Ba=w.getAttribute("aria-owner"),Ca=Ba?O(Ba):j;Ca&&U(Ca.parentNode,"gbto");var da=O(I);if(da){ta(w,da);da.style.visibility="visible"}G=f}var Da=s.dh[f];if(Da)for(y=0;z=Da[y];y++)try{z()}catch(ab){E(ab,"sb","t2")}}}}catch(bb){E(bb,
"sb","t3")}},va=function(a){if(G)try{a=a||window.event;var b=a.target||a.srcElement;if(a.keyCode&&b)if(a.keyCode&&a.keyCode==27)S();else if(b.tagName.toLowerCase()=="a"&&b.className.indexOf("gbgt")!=-1&&(a.keyCode==13||a.keyCode==3)){var c=document.getElementById(G);if(c){var d=c.getElementsByTagName("a");d&&d.length&&d[0].focus&&d[0].focus()}}}catch(e){E(e,"sb","kuh")}},ja=function(){var a=O("gb");if(a){ua(a,"gbpdjs");a=a.getElementsByTagName("a");for(var b=0,c;c=a[b];b++){var d=Ea(c);d&&Fa(c,m(Ga,
d))}}},ka=function(a){var b=Ea(a);b&&Fa(a,m(Ga,b))},Ea=function(a){for(var b=0,c;c=ra[b];b++)if(T(a,c))return c},Fa=function(a,b){var c=function(d,e){return function(f){try{f=f||window.event;var g;var i=f.relatedTarget,t;b:{try{ia(i.parentNode);t=h;break b}catch(D){}t=k}g=t?i:j;var u;if(!(u=d===g))if(d===g)u=k;else{for(;g&&g!==d;)g=g.parentNode;u=g===d}u||e(f,d)}catch(p){E(p,"sb","bhe")}}}(a,b);N(a,"mouseover",c);N(a,"mouseout",c)},Ga=function(a,b,c){try{a+="-hvr";if(b.type=="mouseover"){U(c,a);var d=
document.activeElement;if(d){var e=T(d,"gbgt")||T(d,"gbzt"),f=T(c,"gbgt")||T(c,"gbzt");e&&f&&d.blur()}}else b.type=="mouseout"&&ua(c,a)}catch(g){E(g,"sb","moaoh")}},V=function(a){for(;a&&a.hasChildNodes();)a.removeChild(a.firstChild)},la=function(){S()},ma=function(a){a==G&&S()},W=function(a,b){var c=document.createElement(a);c.className=b;return c},Ha=function(a){if(a&&a.style.visibility=="visible"){sa(a);ta(a)}};x.push(["base",{init:function(a){pa(a)}}]);var X=function(a){o("gbar.pcm",l(this.H,this));o("gbar.paa",l(this.G,this));o("gbar.prm",l(this.Q,this));o("gbar.pge",l(this.j,this));o("gbar.ppe",l(this.n,this));o("gbar.spn",l(this.R,this));o("gbar.spp",l(this.S,this));this.r=this.c=this.h=k;this.J=a.mg||"%1$s";this.I=a.md||"%1$s";this.L=a.g;this.ta=a.d;this.a=a.e;this.b=a.p;this.K=a.m;var b=O("gbmpn");if(b&&(b.firstChild&&b.firstChild.nodeValue?b.firstChild.nodeValue:"")==this.a){b=this.a.indexOf("@");b>=0&&Ia(this,this.a.substring(0,b))}(b=O("gbi4i"))&&
b.loadError&&this.j();(b=O("gbmpi"))&&b.loadError&&this.n();if(!this.h){b=O("gbd4");var c=O("gbmp2"),d=O("gbmpsb");b&&N(b,"click",l(this.O,this),h);if(c&&d){N(c,"click",l(this.t,this));N(d,"click",l(this.t,this))}this.h=h}if(this.L){b=O("gbpm");c=O("gbpms");if(b&&c){var e=c.innerHTML.split("%1$s");if(e.length==2){d=document.createTextNode(e[0]);e=document.createTextNode(e[1]);var f=W("span","gbpms2"),g=document.createTextNode(this.K);V(c);f.appendChild(g);c.appendChild(d);c.appendChild(f);c.appendChild(e);
b.style.display=""}}}if(a.xp){a=O("gbg4");b=O("gbg6");a&&N(a,"mouseover",l(this.o,this));b&&N(b,"mouseover",l(this.o,this))}};X.prototype.O=function(a){try{if(G)for(var b=a.target||a.srcElement;b.tagName.toLowerCase()!="a";){if(b.id=="gbd4"){a.cancelBubble=h;return b}b=b.parentNode}}catch(c){E(c,"sp","kdo")}return j};
X.prototype.t=function(a){try{a=a||window.event;a.cancelBubble=h;a.stopPropagation&&a.stopPropagation();a.preventDefault&&a.preventDefault();var b=O("gbmps");if(b){var c=b.style.display=="none";try{var d=O("gbd4"),e=O("gbmps"),f=O("gbmpdv");if(d&&e&&f){f.style.display=c?"none":"";e.style.display=c?"":"none";Ha(d)}}catch(g){E(g,"sp","tav")}}}catch(i){E(i,"sp","tave")}return k};X.prototype.H=function(){try{var a=O("gbmpas");a&&V(a);this.c=k}catch(b){E(b,"sp","cam")}};
X.prototype.Q=function(){var a=O("gbmpdv"),b=O("gbmps");if(a&&b){a.style.display="";b.style.display="none";if(!this.c){var c=O("gbmpal"),d=O("gbpm");if(c){a.style.width="";b.style.width="";c.style.width="";if(d)d.style.width="1px";var e=P(a).width,f=P(b).width;e=e>f?e:f;if(f=O("gbg4")){f=P(f).width;if(f>e)e=f}if(F&&F.length>1){f=new Number(F[1]);if(f==6||f==7&&document.compatMode=="BackCompat")e+=2}e+="px";a.style.width=e;b.style.width=e;c.style.width=e;if(d)d.style.width=e;this.c=h}}}};
X.prototype.G=function(a,b,c,d,e,f,g,i){try{var t=O("gbmpas");if(t){var D="gbmtc";if(a)D+=" gbmpmta";var u=W("div",D),p=W("div","gbmpph");u.appendChild(p);var n=W(f?"a":"span","gbmpl");U(n,"gbmt");if(f){if(i)for(var y in i)n.setAttribute(y,i[y]);n.href=g;Fa(n,m(Ga,"gbmt"))}u.appendChild(n);var z=W("span","gbmpmn");n.appendChild(z);z.appendChild(document.createTextNode(d||e));if(a){var ba=W("span","gbmpmtc");z.appendChild(ba)}var w=W("span","gbmpme");n.appendChild(w);a=e;if(b)a=this.I.replace("%1$s",
e);else if(c)a=this.J.replace("%1$s",e);w.appendChild(document.createTextNode(a));t.appendChild(u)}}catch(A){E(A,"sp","aa")}};var Ia=function(a,b){var c=O("gbd4"),d=O("gbmpn");if(c&&d){V(d);d.appendChild(document.createTextNode(b));Ha(c)}};X.prototype.j=function(){try{Ja(this,"gbi4i","gbi4id")}catch(a){E(a,"sp","gbpe")}};X.prototype.n=function(){try{Ja(this,"gbmpi","gbmpid")}catch(a){E(a,"sp","ppe")}};var Ja=function(a,b,c){if(a=O(b))a.style.display="none";if(c=O(c))c.style.display=""};
X.prototype.o=function(){try{if(!this.r){this.r=h;var a=O("gbmpi");if(a&&this.b)a.src=this.b}}catch(b){E(b,"sp","spp")}};X.prototype.R=function(a){try{var b=O("gbi4t");(O("gbmpn").firstChild&&O("gbmpn").firstChild.nodeValue?O("gbmpn").firstChild.nodeValue:"")==this.a||Ia(this,a);if((b.firstChild&&b.firstChild.nodeValue?b.firstChild.nodeValue:"")!=this.a){V(b);b.appendChild(document.createTextNode(a))}}catch(c){E(c,"sp","spn")}};
X.prototype.S=function(a){try{var b=O("gbmpi"),c=O("gbi4i");this.b=a(96);if(b)b.src=a(96);if(c)c.src=a(24)}catch(d){E(d,"sp","spp")}};x.push(["prf",{init:function(a){new X(a)}}]);x.push(["il",{init:function(){ha.M();var a=r.D,b;if(!q){a:{b="gbar.logger".split(".");for(var c=aa,d;d=b.shift();)if(c[d]!=j)c=c[d];else{b=j;break a}b=c}q=b||{}}b=q;ea(b.il)=="function"&&b.il(a,void 0)}}]);var Na=function(a,b){if(window.gbar.logger._itl(b))return b;var c=a.stack;if(c){c=c.replace(/\s*$/,"").split("\n");for(var d=[],e=0;e<c.length;e++)d.push(Ka(c[e]));c=d}else c=La();d=c;e=0;for(var f=d.length-1,g=0;g<=f;g++)if(d[g]&&d[g].name.indexOf("_mlToken")>=0){e=g+1;break}e==0&&f--;c=[];for(g=e;g<=f;g++)d[g]&&!(d[g].name.indexOf("_onErrorToken")>=0)&&c.push("> "+Ma(d[g]));d=[b,"&jsst=",c.join("")];e=d.join("");if(!window.gbar.logger._itl(e))return e;if(c.length>2){d[2]=c[0]+"..."+c[c.length-1];
e=d.join("");if(!window.gbar.logger._itl(e))return e}return b};x.push(["er",{init:function(){window.gbar.logger._aem=Na}}]);var Ka=function(a){var b=a.match(Oa);if(b)return new Pa(b[1]||"",b[2]||"",b[3]||"","",b[4]||b[5]||"");if(b=a.match(Qa))return new Pa("",b[1]||"","",b[2]||"",b[3]||"");return j},Oa=RegExp("^    at(?: (?:(.*?)\\.)?((?:new )?(?:[a-zA-Z_$][\\w$]*|<anonymous>))(?: \\[as ([a-zA-Z_$][\\w$]*)\\])?)? (?:\\(unknown source\\)|\\(native\\)|\\((?:eval at )?((?:http|https|file)://[^\\s)]+|javascript:.*)\\)|((?:http|https|file)://[^\\s)]+|javascript:.*))$"),Qa=/^([a-zA-Z_$][\w$]*)?(\(.*\))?@(?::0|((?:http|https|file):\/\/[^\s)]+|javascript:.*))$/,
La=function(){for(var a=[],b=arguments.callee.caller,c=0;b&&c<20;){var d;d=(d=Function.prototype.toString.call(b).match(Ra))?d[1]:"";var e=b,f=["("];if(e.arguments)for(var g=0;g<e.arguments.length;g++){var i=e.arguments[g];g>0&&f.push(", ");typeof i=="string"?f.push('"',i,'"'):f.push(String(i))}else f.push("unknown");f.push(")");a.push(new Pa("",d,"",f.join(""),""));try{if(b==b.caller)break;b=b.caller}catch(t){break}c++}return a},Ra=/^function ([a-zA-Z_$][\w$]*)/,Pa=function(a,b,c,d,e){this.i=a;this.name=
b;this.f=c;this.P=d;this.k=e},Ma=function(a){var b=[a.i?a.i+".":"",a.name?a.name:"anonymous",a.P,a.f?" [as "+a.f+"]":""];if(a.k){b.push(" at ");b.push(a.k)}a=b.join("");for(b=window.location.href.replace(/#.*/,"");a.indexOf(b)>=0;)a=a.replace(b,"[page]");return a=a.replace(/http.*?extern_js.*?\.js/g,"[xjs]")};C(v.v);C(v.z);var Sa,Y;for(Sa=0;Y=s.bnc[Sa];++Sa)if(Y[0]=="m")break;
if(Y&&!Y[1].l){for(var Ta=s.mdc,Ua=s.mdi||{},Va=0,Wa;Wa=x[Va];++Va){var Z=Wa[0],Xa=Ta[Z],Ya=Ua[Z],Za;if(Za=Xa){var $a;if($a=!Ya){var cb;a:{var db=Z,eb=s.mdd;if(eb)try{if(!B){B={};for(var fb=eb.split(/;/),gb=0;gb<fb.length;++gb)B[fb[gb]]=h}cb=B[db];break a}catch(hb){s.logger&&s.logger.ml(hb)}cb=k}$a=!cb}Za=$a}if(Za){C(v.B,Z);try{Wa[1].init(Xa);Ua[Z]=h}catch(ib){s.logger&&s.logger.ml(ib)}C(v.A,Z)}}var jb=s.qd.m;if(jb){s.qd.m=[];for(var kb=0,lb;lb=jb[kb];++kb)try{lb()}catch(mb){s.logger&&s.logger.ml(mb)}}Y[1].l=
h;C(v.w);var nb;a:{for(var ob=0,$;$=s.bnc[ob];++ob)if(($[1].auto||$[0]=="m")&&!$[1].l){nb=k;break a}nb=h}nb&&C(v.u)};}catch(e){window.gbar&&gbar.logger&&gbar.logger.ml(e,{"_sn":"m.init"});}})();
