<?php

namespace App\Exceptions;

use Exception;

class LackingParametersException extends Exception
{
    protected $message = "The request was received but does not contains all of the necessary parameters";
}
