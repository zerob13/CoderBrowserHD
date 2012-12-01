PR.registerLangHandler(
  PR.createSimpleLexer(
    [
      // Whitespace is made up of spaces, tabs and newline characters.
      [PR.PR_PLAIN,       /^[\t\n\r \xA0]+/, null, '\t\n\r \xA0'],

      [PR.PR_STRING,
        /^(?:\'(?:[^\\\'\r\n]|\\.)*(?:\'|$)|\"(?:[^\\\"\r\n]|\\.)*(?:\"|$))/,
        null, '"\'']
    ],
    [
      [PR.PR_KEYWORD,
/^\b(and|or|xor|__FILE__|exception|__LINE__|array|as|break|case|class|const|continue|declare|default|die|do|echo|else|elseif|empty|enddeclare|endfor|endforeach|endif|endswitch|endwhild|eval|exit|extends|for|foreach|function|global|if|include|include_once|isset|list|new|print|require|require_once|return|static|switch|unset|use|var|while|__FUNCTION__|__CLASS__|__METHOD__|final|php_user_filter|interface|implements|instanceof|public|private|protected|abstract|clone|try|catch|throw|cfunction|old_function|this|final|__NAMESPACE__|namespace|goto|__DIR__|true|false|null|TRUE|FALSE|NULL)\b/i,
null],

      [PR.PR_COMMENT, /^\/\/[^\r\n]*/, null],
      [PR.PR_COMMENT, /^\/\*[\s\S]*?(?:\*\/|$)/, null],

      [PR.PR_LITERAL,
        new RegExp(
          '^(?:'
          // A hex number
          + '0x[a-f0-9]+'
          // or an octal or decimal number,
          + '|(?:\\d(?:_\\d+)*\\d*(?:\\.\\d*)?|\\.\\d\\+)'
          // possibly in scientific notation
          + '(?:e[+\\-]?\\d+)?'
          + ')'
          // with an optional modifier like UL for unsigned long
          + '[a-z]*', 'i'),
        null, '0123456789'],

      [PR.PR_VARIABLE, /^(?:\$+\w+)/i]
    ]
  ),
  ['php', 'phtml', 'php5', 'php4']
);