using Microsoft.AspNetCore.Mvc.RazorPages;

namespace dotnet_hello_world.Pages
{
    public class IndexModel : PageModel
    {
        public string Message {get; private set; } = "It's running!";

    }
}
