import React, { useState } from "react";

function GlobalSearch() {
    const [query, setQuery] = useState("");
    const [results, setResults] = useState({});

    const handleSearch = async () => {
        const response = await fetch(`/api/search?query=${encodeURIComponent(query)}`);
        const data = await response.json();
        setResults(data);
    };

    return (
        <div>
            <input 
                type="text" 
                value={query} 
                onChange={(e) => setQuery(e.target.value)} 
                placeholder="Search..."
            />
            <button onClick={handleSearch}>Search</button>

            <div>
                <h3>Search Results</h3>
                {Object.keys(results).map((key) => (
                    <div key={key}>
                        <h4>{key}</h4>
                        <ul>
                            {results[key].map((item, index) => (
                                <li key={index}>{JSON.stringify(item)}</li>
                            ))}
                        </ul>
                    </div>
                ))}
            </div>
        </div>
    );
}

export default GlobalSearch;
