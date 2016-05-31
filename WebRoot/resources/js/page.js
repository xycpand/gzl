// JavaScript Document
//事件基础类
( function() {
	var EventBase = function() {

		this.addListener = function ( type, listener ) {
			getListener( this, type, true ).push( listener );
		}	

		this.removeListener = function ( type, listener ) {
			var listeners = getListener( this, type );
			for( var i = 0; i < listeners.length; i++ ) {
				if( listeners[ i ] == listener ) {
					 listeners.splice( i, 1 );
					 return;
				}
			}
		}	
		
		this.fireEvent = function ( type ) {
			var listeners = getListener( this, type ),
				r, t, k;
			if ( listeners ) {
				k = listeners.length;
				while ( k -- ) {
					t = listeners[k].apply( this, arguments );
					if ( t !== undefined ) {
						r = t;
					}
				}    
			}
			if ( t = this['on' + type.toLowerCase()] ) {
				r = t.apply( this, arguments );
			}
			return r;
		}		
	}
		
	function getListener( obj, type, force ) {
		var allListeners;
		type = type.toLowerCase();
		return ( ( allListeners = ( obj.__allListeners || force && ( obj.__allListeners = {} ) ) )
			&& ( allListeners[type] || force && ( allListeners[type] = [] ) ) );
	}
	
	window[ 'EventBase' ] = EventBase;
})();


//分页类
var Page = function(pageCanvas)
{
    this.recordCount;
    this.pageSize; 
    this.numericButtonCount;
    this.pageCanvas = pageCanvas; 
    this.pageIndex = 1;
}

Page.prototype = new EventBase();

Page.prototype.getPageHtml = function()
{
     this.pageCount = Math.ceil(this.recordCount / this.pageSize);
     var prev = this.pageIndex == 1 ? " <span class='page_up_onthis'>上一页</span>" : " <span class='page_up'><a href='javascript:;' pageindex='"+ (this.pageIndex - 1) +"'>上一页</a></span> ";
     var next = this.pageCount <= this.pageIndex ? " <span class='page_next_onthis'>下一页</span>" : " <span class='page_next'><a href='javascript:;' pageIndex='"+ (this.pageIndex + 1) +"'>下一页</a></span>";  
     var first = this.pageIndex == 1 ? "<span class='onthis'>1</span>..." : "<span><a href='javascript:;' pageindex='1'>1</a></span>...";
     var last = this.pageCount <= this.pageIndex ? "...<span class='onthis'>" + this.pageCount + "</span>" : "...<span><a href='javascript:;' pageindex='"+ (this.pageCount) +"'>"+ this.pageCount +"</a></span>";  
     var pageStr = ""

     var pageMathIndex =  Math.floor(this.numericButtonCount / 2);
     var pageStartIndex ;
     var pageEndIndex;

     if(this.pageCount < this.numericButtonCount)
     {
         pageStartIndex = 1
         pageEndIndex = this.pageCount ;
     }
     else
     {
         if(this.pageCount - pageMathIndex < this.pageIndex)
         {
             pageStartIndex = this.pageCount - this.numericButtonCount + 1;
             pageEndIndex = this.pageCount;
         } 
         else
         {
             if(this.pageIndex - pageMathIndex < 1)
             {
                 pageStartIndex = 1;
                 pageEndIndex = this.numericButtonCount;
             }
             else
             {
                 pageStartIndex = this.pageIndex - pageMathIndex;
                 pageEndIndex = this.pageIndex + pageMathIndex;
             }
         }
     
     }
     
     for( var i = pageStartIndex; i <=  pageEndIndex ; i ++)
     {
         if(this.pageIndex == i) pageStr += " <span class='curr onthis'>"+ i +"</span>"
             else pageStr += " <span><a href='javascript:;' pageindex='"+ i +"'>"+ i +"</a></span>";
     }
     
	 if(pageStartIndex == 1) first = '';
	 if(pageEndIndex == this.pageCount) last = '';
     //pageStr = first + prev + pageStr + next + last;
     pageStr = prev + first + pageStr + last + next;
	 return pageStr;
}


Page.prototype.onPageChanged = function(pageIndex)
{
    this.pageIndex = pageIndex;
    this.fireEvent( 'pageChanged' );
}

Page.prototype.pageEvent = function(page)
{
    this.onclick = function(e)
    {
        e = e || window.event;
        t = e .target || e.srcElement;
        if(t.tagName == "A") page.onPageChanged(parseInt(t.getAttribute("pageindex")));
    }
}

Page.prototype.render = function()
{
    var pageCanvas = document.getElementById(this.pageCanvas);
    pageCanvas.innerHTML = this.getPageHtml();
    this.pageEvent.call(pageCanvas,this);
}

Page.prototype.initialize = function()
{
    this.onPageChanged(this.pageIndex);
}

