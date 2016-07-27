package com.redhat.theses

class Link implements Serializable {
    String url
    
    static mapping = {
        id generator: 'sequence', params: [sequence: 'seq_link_id']
        version false
    }
    
    String toString() {
         url  
    }       
        
}
