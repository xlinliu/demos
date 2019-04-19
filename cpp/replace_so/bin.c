#include<stdio.h>
#include <unistd.h>
#include<dlfcn.h>
#include"so.h"

int main( )

{

void *dl_handle;

void (*func)();

char *error;
while (1) {
/* Open the shared object */

dl_handle = dlopen("/home/xlinliu/demos/replace_so/libso.so", RTLD_NOW);

if (dl_handle==NULL) {

printf( "!!! %sn", dlerror() );

return -1;

}



/* Resolve the symbol (method) from the object */

func = dlsym( dl_handle, "foo" );

error = dlerror();

if (error != NULL) {

printf( "!!! %sn", error );

return -1;

}

/* Call the resolved method and print the result */

(*func)();

sleep(1);

dlclose( dl_handle );
}

/* Close the object */


return 0;

}
